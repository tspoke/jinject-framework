package com.jinject.inject.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a mapper for an injected object to optimize the process of injection and reflection.
 * @author Thibaud Giovannetti
 *
 */
public class InjectorBindingMapper {
	private Class<?> clazz;
	private Map<Field, Object> bindingForField;
	
	private Constructor<?> constructor;
	private List<Class<?>> constructorFields;
	private boolean isConstructorInjectable = false;
	
	public InjectorBindingMapper(Class<?> c) {
		clazz = c;
		bindingForField = new HashMap<>();
		constructorFields = new ArrayList<>(4);
	}
	
	public void setConstructor(Constructor<?> c){
		constructor = c;
	}
	
	public Constructor<?> getConstructor(){
		return constructor;
	}
	
	public void addConstructorBinding(Class<?> param){
		isConstructorInjectable = true;
		constructorFields.add(param);
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
	 * Get all binding for the constructor of this class
	 * @return
	 */
	public List<Class<?>> getBindingsForConstructor(){
		return constructorFields;
	}
	
	/**
	 * Get the class for these bindings
	 * @return
	 */
	public Class<?> getClassType(){
		return clazz;
	}
	
	public boolean isConstructorInjectable(){
		return isConstructorInjectable;
	}
}
