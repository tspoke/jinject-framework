package com.jinject.inject.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.jinject.bind.api.IBinder;
import com.jinject.bind.exception.BindingResolverException;
import com.jinject.inject.api.IInjector;
import com.jinject.inject.api.Inject;

/**
 * Inject, to an instance, values from a binder or from a existing mapping
 * @author Thibaud Giovannetti
 *
 */
public class Injector implements IInjector {

	/**
	 * Injection Mappers for classes to optimize the process
	 */
	private Map<Class<?>, InjectorBindingMapper> mappers;
	
	public Injector() {
		mappers = new HashMap<>();
	}
	
	
	@Override
	public InjectorBindingMapper reflectClass(Class<?> clazz, IBinder binder) throws InstantiationException, IllegalAccessException, BindingResolverException {
		if(mappers.containsKey(clazz))
			return mappers.get(clazz);
		
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
		mappers.put(clazz, injectorBinding);
		return injectorBinding;
	}


	@Override
	public void inject(Object instance, InjectorBindingMapper mapper) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		Map<Field, Object> mapping = mapper.getBindingsForFields();
		
		for(Entry<Field, Object> f : mapping.entrySet()){
			Object expected = f.getValue();
			Object toBind = null;
			
			if(expected instanceof Class)
				toBind = ((Class<?>) expected).newInstance();
			else 
				toBind = expected;
			
			inject(toBind); // recursive injection
			f.getKey().set(instance, toBind);
		}
	}
	
	@Override
	public InjectorBindingMapper inject(Object instance, IBinder binder) throws InstantiationException, BindingResolverException, IllegalArgumentException, IllegalAccessException {
		InjectorBindingMapper injectorBinding = reflectClass(instance.getClass(), binder);
		inject(instance, injectorBinding);
		return injectorBinding;
	}
	
	@Override
	public void inject(Object instance) throws IllegalArgumentException, IllegalAccessException, InstantiationException{
		InjectorBindingMapper injectorBinding = mappers.get(instance.getClass());
		if(injectorBinding == null)
			throw new IllegalArgumentException("The injector can't determine the mapper to inject in this object. Consider calling reflectClass() before");
		
		inject(instance, injectorBinding);
	}
	
}
