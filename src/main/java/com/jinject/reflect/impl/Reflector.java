package com.jinject.reflect.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import com.jinject.bind.api.IBinder;
import com.jinject.bind.exception.BindingResolverException;
import com.jinject.inject.api.Inject;
import com.jinject.inject.api.InjectConstructor;
import com.jinject.inject.impl.InjectorBindingMapper;
import com.jinject.reflect.api.IReflector;

public class Reflector implements IReflector {
	
	@Override
	public InjectorBindingMapper reflectClass(Class<?> clazz, IBinder binder) throws InstantiationException, IllegalAccessException, BindingResolverException {
		Field[] fields = clazz.getDeclaredFields();
		InjectorBindingMapper injectorBinding = new InjectorBindingMapper(clazz);
		
		// Constructors
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		boolean constructorInjectableFound = false;
		
		for(Constructor<?> c : constructors){
			InjectConstructor annotation = c.getDeclaredAnnotation(InjectConstructor.class);
			if(annotation != null && !constructorInjectableFound){ // constructor found
				Class<?>[] paramsTypes = c.getParameterTypes();
				injectorBinding.setConstructor(c);
				for(Class<?> param : paramsTypes){
					injectorBinding.addConstructorBinding(param);
				}
				constructorInjectableFound = true;
				// we could break the for here but I don't to check the class for another annoted constructor and throw an exception to disallow it !
			}
			else if(annotation != null && constructorInjectableFound){
				throw new BindingResolverException("Reflector : You can only declare one @InjectConstructor in a class. Concerned class : " + clazz);
			}
		}
		
		// Fields
		for(Field f : fields){
			Inject injectNotation = f.getDeclaredAnnotation(Inject.class);
			if(injectNotation != null){
				f.setAccessible(true); // access to private fields
				
				String bindToName = null;
				if (injectNotation.value().equals(""))
					bindToName = null;
				else
					bindToName = injectNotation.value();
				
				Object binding = null;
				try {
					binding = binder.getBinding(f.getType(), bindToName);
					if(binding == null)
						throw new BindingResolverException("ReflectorMAN : There is no binding for [" + f.getType() + "] for name "+f.getName()+". \nYou probably forget to add it to the binder. " );
					if(binding instanceof Class)
						reflectClass((Class<?>) binding, binder);
					else
						reflectClass(binding.getClass(), binder);
					
					injectorBinding.addBinding(f, binding);
				}
				catch(BindingResolverException e){
					throw new BindingResolverException("Reflector : There is no binding for [" + f.getType() + "] for name "+f.getName()+". \nYou probably forget to add it to the binder. \nPrevious error : " + e.getMessage());
				}
				catch(ClassCastException e){
					throw new BindingResolverException("Reflector : Looks like a cast gone bad : " + f.getType() + " => " + e.getMessage());
				}
			}
		}
		
		return injectorBinding;
	}
}
