package com.jinject.bind.impl;

import java.util.HashMap;
import java.util.Map;

import com.jinject.bind.api.IBinding;
import com.jinject.bind.exception.BindingResolverException;

public class Binding implements IBinding {
	private Class<?> key;
	private Map<String, Object> bindings;

	private Object temporary;	
	private Object defaultBinding;
	private boolean defaultBindingIsDuplicate = false;
	
	// TODO check if the TO() respect the k type class to provide a safe cast
	public Binding(Class<?> k) {
		key = k;
		bindings = new HashMap<>();
	}

	@Override
	public IBinding to(Object o) {		
		if(o instanceof Class && !key.isAssignableFrom((Class<?>) o))
			throw new ExceptionInInitializerError("You can't bind an object or a class who is not a subtype or a type of the binding key");
		else if(!(o instanceof Class) && !key.isInstance(o))
			throw new ExceptionInInitializerError("You can't bind an object or a class who is not a subtype or a type of the binding key");
		
		if(defaultBinding == null || defaultBindingIsDuplicate){
			defaultBinding = o;
			defaultBindingIsDuplicate = false;
		}
		
		temporary = o;
		return this;
	}


	@Override
	public IBinding toName(String name) {
		if(temporary == null)
			throw new ExceptionInInitializerError("There is no object bind to this key. Please use to() before toName()");
		
		bindings.put(name, temporary);
		
		if(defaultBinding != null && defaultBinding == temporary){
			defaultBindingIsDuplicate = true;
			defaultBinding = null;
		}
		temporary = null;
		
		return this;
	}


	@Override
	public Object getBinding(String name) throws BindingResolverException, InstantiationException, IllegalAccessException {
		if(name == null){
			if(defaultBinding != null){
				return defaultBinding;
			}
			else
				throw new BindingResolverException("There is no default binding for this key");
		}
		else {
			// we can add return defaultBinding if not found, but it's more logic like this btw...
			// Actually, a defaultValue can be very different from a name value. So it's safer that way
			if(bindings.containsKey(name) || bindings.get(name) != null)
				return bindings.get(name);
			else
				throw new BindingResolverException("There is no binding for this key name");
		}
	}


	@Override
	public Object getBinding() throws BindingResolverException, InstantiationException, IllegalAccessException {
		return getBinding(null);
	}

	@Override
	public IBinding unbind() {
		defaultBinding = null;
		return this;
	}

	@Override
	public IBinding unbind(String name) {
		bindings.remove(name);
		return this;
	}

	@Override
	public IBinding unbindAll() {
		bindings.clear();
		return this;
	}
}
