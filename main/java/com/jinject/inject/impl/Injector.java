package com.jinject.inject.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jinject.bind.api.IBinder;
import com.jinject.bind.exception.BindingResolverException;
import com.jinject.inject.api.IInjector;
import com.jinject.reflect.api.IReflector;

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
	
	/**
	 *Reflector used to reflect classes
	 */
	private final IReflector reflector;
	
	
	public Injector(IReflector reflector) {
		mappers = new HashMap<>();
		this.reflector = reflector;
	}
	

	@Override
	public List<Object> inject(Object instance, InjectorBindingMapper mapper) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		if(!mappers.containsKey(instance.getClass()))
			mappers.put(instance.getClass(), mapper);

		Map<Field, Object> mapping = mapper.getBindingsForFields();
		
		List<Object> instancesInjected = new ArrayList<>();
		for(Entry<Field, Object> f : mapping.entrySet()){
			Object expected = f.getValue();
			Object toBind = null;
			
			if(expected instanceof Class)
				toBind = ((Class<?>) expected).newInstance();
			else 
				toBind = expected;
			
			instancesInjected.add(toBind);
			f.getKey().set(instance, toBind);
		}
		return instancesInjected;
	}
	
	
	@Override
	public InjectorBindingMapper inject(Object instance, IBinder binder) throws InstantiationException, BindingResolverException, IllegalArgumentException, IllegalAccessException {
		InjectorBindingMapper injectorBinding = null;
		Class<?> clazz = instance.getClass();
		
		if(mappers.containsKey(clazz))
			injectorBinding = mappers.get(clazz);
		else 
			injectorBinding = reflector.reflectClass(instance.getClass(), binder);
		
		List<Object> instancesInjected = inject(instance, injectorBinding);
		for(Object i : instancesInjected){
			inject(i, binder); // we ensure that all sub instance have been injected too
		}
		return injectorBinding;
	}
	
	@Override
	public void inject(Object instance) throws IllegalArgumentException, IllegalAccessException, InstantiationException{
		InjectorBindingMapper injectorBinding = mappers.get(instance.getClass());
		if(injectorBinding == null)
			throw new IllegalArgumentException("The injector can't determine the mapper to inject in this object ["+instance.getClass()+"]. Consider calling the (Object, IBinder) version instead.");
		
		inject(instance, injectorBinding);
	}


	@Override
	public boolean register(Class<?> o, IBinder binder) throws InstantiationException, IllegalAccessException, BindingResolverException {
		if(mappers.containsKey(o))
			return false;
		
		mappers.put((Class<?>)o, reflector.reflectClass((Class<?>) o, binder));
		return true;
	}
	
}
