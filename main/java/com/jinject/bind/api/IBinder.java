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
	Object getBinding(Class<?> key) throws InstantiationException, IllegalAccessException, BindingResolverException;
	
	
	/**
	 * Get binding for a key of type T and with a unique String identifier
	 * @param key
	 * @param name
	 * @return The binding who extends T and is designed with this identifier
	 * @throws BindingResolverException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	Object getBinding(Class<?> key, String name) throws InstantiationException, IllegalAccessException, BindingResolverException;
	
	/**
	 * Prepare the binding of an object
	 * @param clazz
	 * @return
	 */
	IBinding bind(Class<?> key);
	
	/**
	 * Remove all bindings associates to this key
	 * @param key
	 */
	void unbindAll(Class<?> key);
	
	/**
	 * Unbind the default binding associate to this key
	 * @param key
	 */
	void unbind(Class<?> key);
	
	/**
	 * Unbind the binding associate to this key and to this name
	 * @param key
	 * @param name
	 */
	void unbind(Class<?> key, String name);
}
