package com.jinject.bind.api;

import com.jinject.bind.exception.BindingResolverException;

/**
 * A binder is the container for all bindings
 * @author Thibaud Giovannetti
 *
 */
public interface IBinder {
	/**
	 * Get binding for a key of T type 
	 * @param 	key
	 * @return	The binding who extends T
	 * @throws BindingResolverException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	Object getBinding(Object key) throws InstantiationException, IllegalAccessException, BindingResolverException;
	
	
	/**
	 * Get binding for a key of type T and with a unique String identifier
	 * @param key
	 * @param name
	 * @return The binding who extends T and is designed with this identifier
	 * @throws BindingResolverException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	Object getBinding(Object key, Object name) throws InstantiationException, IllegalAccessException, BindingResolverException;
	
	/**
	 * Prepare the binding of an object
	 * @param clazz
	 * @return
	 */
	IBinding bind(Object key);
	
	/**
	 * Remove all bindings associates to this key
	 * @param key
	 */
	void unbindAll(Object key);
	
	/**
	 * Unbind the default binding associate to this key
	 * @param key
	 */
	void unbind(Object key);
	
	/**
	 * Unbind the binding associate to this key and to this name
	 * @param key
	 * @param name
	 */
	void unbind(Object key, Object name);
}
