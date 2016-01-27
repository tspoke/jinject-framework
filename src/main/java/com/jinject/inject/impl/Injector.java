package com.jinject.inject.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ClassUtils;

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
	public Map<Field, Object> injectTypesWithInstances(Object instance, Map<Field, Object> mapping, Map<Class<?>, Object> bindings) throws IllegalArgumentException, IllegalAccessException{
		Map<Field, Object> notBind = new HashMap<>();
		
		for(Entry<Field, Object> f : mapping.entrySet()){
			Object expected = f.getValue();
			
			if(expected instanceof Class && bindings.containsKey(expected)){
				f.getKey().set(instance, bindings.get(expected));
			}
			else 
				notBind.put(f.getKey(), f.getValue());
		}
		return notBind;
	}
	
	@Override
	public void injectFields(Object instance, IBinder binder, Map<Field, Object> mapping, boolean recursiveInjection) throws IllegalArgumentException, IllegalAccessException, InstantiationException, BindingResolverException, InvocationTargetException, NoSuchMethodException, SecurityException{
		for(Entry<Field, Object> f : mapping.entrySet()){
			Object expected = f.getValue();
			Object toBind = null;
			
			if(expected instanceof Class){
				Class<?> clazz = (Class<?>) expected;
				if(!ClassUtils.isPrimitiveOrWrapper(clazz))
					toBind = clazz.newInstance();
				else if (ClassUtils.isPrimitiveWrapper(clazz))
					toBind = clazz.getConstructor(ClassUtils.wrappersToPrimitives(clazz)).newInstance(0);
				else {
					toBind = ClassUtils.primitiveToWrapper(clazz).getConstructor(clazz).newInstance(0);;
				}
			}
			else 
				toBind = expected;
			
			if(recursiveInjection)
				inject(toBind, binder); // recursive
			
			f.getKey().set(instance, toBind);
		}
	}
	
	@Override
	public Object injectConstructor(Object object, IBinder binder, Constructor<?> constructor, List<Class<?>> bindings) throws BindingResolverException, InstantiationException, IllegalAccessException, IllegalArgumentException{
		Object[] objs = new Object[bindings.size()];
		int i = 0;
		for(Class<?> param : bindings){
			Object o = binder.getBinding(param);
			o = inject(o, binder);
			objs[i++] = o;
		}
		
		try {
			return constructor.newInstance(objs);
		} 
		catch (InvocationTargetException e) {
			throw new BindingResolverException("Failed trying to instantiate object with its constructor @InjectConstructor." + "\nInitial InvocationTargetException message : " + e.getMessage());
		}
	}
	
	

	@Override
	public Object inject(Object object, InjectorBindingMapper mapper, IBinder binder) throws IllegalArgumentException, IllegalAccessException, InstantiationException, BindingResolverException {
		Class<?> clazz = (object instanceof Class) ? (Class<?>) object : object.getClass();
		
		if(!mappers.containsKey(clazz))
			mappers.put(clazz, mapper);
		
		Object instance = null;
		// construct object if class
		if(object instanceof Class && mapper.isConstructorInjectable()){
			instance = injectConstructor(object, binder, mapper.getConstructor(), mapper.getBindingsForConstructor());
		}
		else if(object instanceof Class)
			instance = ((Class<?>) object).newInstance();
		else
			instance = object;
		
		// Fields
		try {
			injectFields(instance, binder, mapper.getBindingsForFields(), true);
		} 
		catch (InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new BindingResolverException("Injector : Cannot inject Fields.\nPrevious error : " + e.getMessage());
		}
		
		return instance;
	}

	
	@Override
	public Object inject(Object object, IBinder binder) throws InstantiationException, BindingResolverException, IllegalArgumentException, IllegalAccessException {
		return inject(object, getMapperForClass(object, binder), binder);
	}
	

	@Override
	public boolean register(Class<?> o, IBinder binder) throws InstantiationException, IllegalAccessException, BindingResolverException {
		if(mappers.containsKey(o))
			return false;
		
		mappers.put((Class<?>)o, reflector.reflectClass((Class<?>) o, binder));
		return true;
	}


	@Override
	public InjectorBindingMapper getMapperForClass(Object object, IBinder binder) throws InstantiationException, IllegalAccessException, BindingResolverException {
		Class<?> clazz = (object instanceof Class) ? (Class<?>) object : object.getClass();
		
		if(!mappers.containsKey(clazz))
			mappers.put(clazz, reflector.reflectClass(clazz, binder));
		
		return mappers.get(clazz);
	}
	
}
