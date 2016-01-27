package com.jinject.inject.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.jinject.bind.api.IBinder;
import com.jinject.bind.exception.BindingResolverException;
import com.jinject.inject.impl.InjectorBindingMapper;

/**
 * Inject, to an instance, values from a binder or from a existing mapping
 * @author Thibaud Giovannetti
 *
 */
public interface IInjector {
	/**
	 * Safest way to inject an instance with its dependencies. If a mapper exists it will be used, else a new mapper will be create.
	 * This method can be slow in the first call because it uses reflection to determine annotations and values to inject.
	 * @param instance
	 * @param binder
	 * @return InjectorBindingMapper 	A container of mappings <Field, Binding> for this specific class.
	 * @throws InstantiationException
	 * @throws BindingResolverException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	Object inject(Object instance, IBinder binder) throws InstantiationException, BindingResolverException, IllegalArgumentException, IllegalAccessException;
	
	/**
	 * Fastest method to inject values in an instance with a mapper.
	 * @param instance
	 * @param mapper
	 * @return A list of injected objects who can need to be injected too
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException 
	 * @throws BindingResolverException 
	 */
	Object inject(Object object, InjectorBindingMapper mapper, IBinder binder) throws IllegalArgumentException, IllegalAccessException, InstantiationException, BindingResolverException;
	
	
	/**
	 * Try to inject value in an instance. If the mapper doesn't exists an error will occurs.
	 * @param instance
	 * @param mapper
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException 
	 */
	// Object inject(Object instance) throws IllegalArgumentException, IllegalAccessException, InstantiationException;
	
	/**
	 * Call its reflector to pre-reflect class and add its mapping in the cache.
	 * @param clazz
	 * @param binder
	 * @return boolean Return true if the class has never been reflected 
	 * @throws BindingResolverException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	boolean register(Class<?> clazz, IBinder binder) throws InstantiationException, IllegalAccessException, BindingResolverException;
	
	/**
	 * Get the mapper for the specified class
	 * @param clazz
	 * @return
	 * @throws BindingResolverException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	InjectorBindingMapper getMapperForClass(Object object, IBinder binder) throws InstantiationException, IllegalAccessException, BindingResolverException;
	
	/**
	 * Try to instantiate & inject an object via its annoted constructor.
	 * @param object
	 * @param binder
	 * @param constructor
	 * @param bindings
	 * @return
	 * @throws BindingResolverException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	Object injectConstructor(Object object, IBinder binder, Constructor<?> constructor, List<Class<?>> bindings) throws BindingResolverException, InstantiationException, IllegalAccessException, IllegalArgumentException;
	
	/**
	 * Inject an instance for these specifies fields
	 * @param instance
	 * @param binder
	 * @param mapping
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws BindingResolverException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 */
	void injectFields(Object instance, IBinder binder, Map<Field, Object> mapping, boolean recursiveInjection) throws IllegalArgumentException, IllegalAccessException, InstantiationException, BindingResolverException, InvocationTargetException, NoSuchMethodException, SecurityException;
	
	/**
	 * Inject specific instances to an object and return all not injected fields
	 * @param instance
	 * @param mapping
	 * @param bindings
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	Map<Field, Object> injectTypesWithInstances(Object instance, Map<Field, Object> mapping, Map<Class<?>, Object> bindings) throws IllegalArgumentException, IllegalAccessException;
}