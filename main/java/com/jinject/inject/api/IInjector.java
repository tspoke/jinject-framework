package com.jinject.inject.api;

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
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException 
	 */
	void inject(Object instance, InjectorBindingMapper mapper) throws IllegalArgumentException, IllegalAccessException, InstantiationException;
	
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
	 * This method create a InjectorBindingMapper for a specific class. It uses reflection to get annoted fields. 
	 * Consider using this method to pre-build all classes who need to be injected to improve performances.
	 * @param clazz
	 * @param binder
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws BindingResolverException
	 */
	InjectorBindingMapper reflectClass(Class<?> clazz, IBinder binder) throws InstantiationException, IllegalAccessException, BindingResolverException;
}