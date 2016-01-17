package com.jinject.inject.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a mapper for an injected object to optimize the process of injection and reflection.
 * @author Thibaud Giovannetti
 *
 */
public class InjectorBindingMapper {
	private Class<?> clazz;
	private Map<Field, Object> bindingForField;
	
	public InjectorBindingMapper(Class<?> c) {
		clazz = c;
		bindingForField = new HashMap<>();
	}
	
	/**
	 * Set a binding to a specific field
	 * @param f
	 * @param binding
	 */
	public void addBinding(Field f, Object binding){
		bindingForField.put(f, binding);
	}
	
	/**
	 * Get all binding for this class
	 * @return
	 */
	public Map<Field, Object> getBindingsForFields(){
		return bindingForField;
	}
	
	/**
	 * Get the class for these bindings
	 * @return
	 */
	public Class<?> getClassType(){
		return clazz;
	}
}
