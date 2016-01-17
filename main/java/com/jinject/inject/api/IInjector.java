package com.jinject.inject.api;

import java.util.List;

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
	InjectorBindingMapper inject(Object instance, IBinder binder) throws InstantiationException, BindingResolverException, IllegalArgumentException, IllegalAccessException;
	
	
	/**
	 * Fastest method to inject values in an instance with a mapper.
	 * @param instance
	 * @param mapper
	 * @return A list of injected objects who can need to be injected too
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException 
	 */
	List<Object> inject(Object instance, InjectorBindingMapper mapper) throws IllegalArgumentException, IllegalAccessException, InstantiationException;
	
	/**
	 * Try to inject value in an instance. If the mapper doesn't exists an error will occurs.
	 * @param instance
	 * @param mapper
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException 
	 */
	void inject(Object instance) throws IllegalArgumentException, IllegalAccessException, InstantiationException;
	
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
}