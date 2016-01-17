package com.jinject.reflect.impl;

import java.lang.reflect.Field;

import com.jinject.bind.api.IBinder;
import com.jinject.bind.exception.BindingResolverException;
import com.jinject.inject.api.Inject;
import com.jinject.inject.impl.InjectorBindingMapper;
import com.jinject.reflect.api.IReflector;

public class Reflector implements IReflector {
	
	@Override
	public InjectorBindingMapper reflectClass(Class<?> clazz, IBinder binder) throws InstantiationException, IllegalAccessException, BindingResolverException {
		Field[] fields = clazz.getDeclaredFields();
		InjectorBindingMapper injectorBinding = new InjectorBindingMapper(clazz);

		for(Field f : fields){
			Inject injectNotation = f.getDeclaredAnnotation(Inject.class);
			if(injectNotation != null){
				String bindToName = null;
				if (injectNotation.value().equals(""))
					bindToName = null;
				else
					bindToName = injectNotation.value();
				
				Object binding = null;
				try {
					binding = binder.getBinding(f.getType(), bindToName);
					if(binding instanceof Class)
						reflectClass((Class<?>) binding, binder);
					else
						reflectClass(binding.getClass(), binder);
					
					injectorBinding.addBinding(f, binding);
				}
				catch(BindingResolverException e){
					throw new BindingResolverException("There is no binding for [" + f.getType() + "]. You probably forget to add it to the binder.");
				}
				catch(ClassCastException e){
					throw new BindingResolverException("Looks like a cast gone bad : " + f.getType() + " => " + e.getMessage());
				}
			}
		}
		
		return injectorBinding;
	}
}
