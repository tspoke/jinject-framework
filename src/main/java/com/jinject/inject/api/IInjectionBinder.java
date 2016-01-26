package com.jinject.inject.api;

import com.jinject.bind.exception.BindingResolverException;

/**
 * An injection binder is able to get instance from a binding system and add objects to it.
 * @author Thibaud Giovannetti
 *
 */
public interface IInjectionBinder {
	/**
	 * 
	 * @param key
	 * @param name
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws BindingResolverException
	 */
	Object getInstance(Object key, Object name);

	/**
	 * 
	 * @param key
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws BindingResolverException
	 */
	Object getInstance(Object key);

	
	/**
	 * Register an object and inject its dependencies
	 * @return the same object injected
	 */
	Object inject(Object o);
}
