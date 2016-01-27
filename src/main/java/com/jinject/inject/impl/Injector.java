package com.jinject.inject.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
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
	public Object inject(Object object, InjectorBindingMapper mapper, IBinder binder) throws IllegalArgumentException, IllegalAccessException, InstantiationException, BindingResolverException {
		Class<?> clazz = (object instanceof Class) ? (Class<?>) object : object.getClass();
		
		if(!mappers.containsKey(clazz))
			mappers.put(clazz, mapper);
		
		Object instance = null;
		// construct object if class
		if(object instanceof Class && mapper.isConstructorInjectable()){
			Object[] objs = new Object[mapper.getBindingsForConstructor().size()];
			int i = 0;
			for(Class<?> param : mapper.getBindingsForConstructor()){
				Object o = binder.getBinding(param);
				o = inject(o, binder);
				objs[i++] = o;
			}
			
			try {
				instance = mapper.getConstructor().newInstance(objs);
			} 
			catch (InvocationTargetException e) {
				throw new BindingResolverException("Failed trying to instantiate object with its constructor @InjectConstructor." + "\nInitial InvocationTargetException message : " + e.getMessage());
			}
		}
		else if(object instanceof Class)
			instance = ((Class<?>) object).newInstance();
		else
			instance = object;
		
		// Fields
		Map<Field, Object> mapping = mapper.getBindingsForFields();
		for(Entry<Field, Object> f : mapping.entrySet()){
			Object expected = f.getValue();
			Object toBind = null;
			
			if(expected instanceof Class)
				toBind = ((Class<?>) expected).newInstance();
			else 
				toBind = expected;
			
			inject(toBind, binder); // recursive
			f.getKey().set(instance, toBind);
		}
		return instance;
	}

	
	@Override
	public Object inject(Object object, IBinder binder) throws InstantiationException, BindingResolverException, IllegalArgumentException, IllegalAccessException {
		InjectorBindingMapper injectorBinding = null;
		Class<?> clazz = (object instanceof Class) ? (Class<?>) object : object.getClass();
		
		if(mappers.containsKey(clazz))
			injectorBinding = mappers.get(clazz);
		else 
			injectorBinding = reflector.reflectClass(clazz, binder);
		
		return inject(object, injectorBinding, binder);
	}
	
	/*
	@Override
	public Object inject(Object object) throws IllegalArgumentException, IllegalAccessException, InstantiationException{
		InjectorBindingMapper injectorBinding = null;
		Class<?> clazz = (object instanceof Class) ? (Class<?>) object : object.getClass();
		injectorBinding = mappers.get(clazz);
		
		if(injectorBinding == null)
			throw new IllegalArgumentException("The injector can't determine the mapper to inject in this object ["+object+"]. Consider calling the (Object, IBinder) version instead.");
		
		return inject(object, injectorBinding);
	}
	*/

	@Override
	public boolean register(Class<?> o, IBinder binder) throws InstantiationException, IllegalAccessException, BindingResolverException {
		if(mappers.containsKey(o))
			return false;
		
		mappers.put((Class<?>)o, reflector.reflectClass((Class<?>) o, binder));
		return true;
	}
	
}
