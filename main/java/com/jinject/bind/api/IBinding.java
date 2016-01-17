package com.jinject.bind.api;

import java.util.Map;

import com.jinject.bind.exception.BindingResolverException;

/**
 * A binding is a map of key/value for a special reference key
 * @author Thibaud Giovannetti
 *
 */
public interface IBinding {
	
	/**
	 * Bind to an instance or value
	 * @param value
	 * @return
	 */
	IBinding to(Object value);
	
	/**
	 * Bind the last binding to an object
	 * @param name
	 * @return
	 */
	IBinding toName(String name);
	
	/**
	 * Lock the binding. This method gives you the capacity to protect instances.
	 * @return
	 */
	IBinding lock();
	
	/**
	 * Get the binding for this identifier
	 * @param name
	 * @return
	 * @throws BindingResolverException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	Object getBinding(String name) throws BindingResolverException, InstantiationException, IllegalAccessException;
	
	/**
	 * Get the default binding
	 * @return
	 * @throws BindingResolverException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	Object getBinding() throws BindingResolverException, InstantiationException, IllegalAccessException;
	
	/**
	 * Get all bindings for this IBinding object
	 * @return
	 */
	Map<String, Object> getBindings();

	/**
	 * Unbind the default binding
	 * @param value
	 * @return
	 */
	IBinding unbind();

	/**
	 * Unbind a binding specified with a name
	 * @param value
	 * @return
	 */
	IBinding unbind(String name);
	
	/**
	 * Unbind all binding
	 * @param value
	 * @return
	 */
	IBinding unbindAll();
}
