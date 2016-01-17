package com.jinject.bind.impl;

import java.util.HashMap;
import java.util.Map;

import com.jinject.bind.api.IBinding;
import com.jinject.bind.exception.BindingResolverException;

public class Binding implements IBinding {
	private Class<?> key;
	private boolean locked = false;
	private Map<String, Object> bindings;
	private Object temporary;
	
	public Binding(Class<?> k) {
		key = k;
		bindings = new HashMap<>();
	}

	@Override
	public IBinding to(Object o) {
		if(locked)
			throw new ExceptionInInitializerError("This binding is locked !");
		if((o instanceof Class && !key.isAssignableFrom((Class<?>) o)) || (!(o instanceof Class) && !key.isInstance(o)))
			throw new ExceptionInInitializerError("You can't bind an object or a class who is not a subtype or a type of the binding key");
		
		if(temporary != null)
			bindings.put(null, temporary);
		else if(!bindings.containsKey(null))
			bindings.put(null, o);
		
		temporary = o;
		return this;
	}


	@Override
	public IBinding toName(String name) {
		if(temporary == null)
			throw new ExceptionInInitializerError("There is no object bind to this key. Please use to() before toName()");

		bindings.put(name, temporary);
		if(bindings.get(null) == temporary)
			bindings.remove(null);
		temporary = null;
		
		return this;
	}

	@Override
	public IBinding lock() {
		locked = true;
		return this;
	}


	@Override
	public Object getBinding(String name) throws BindingResolverException, InstantiationException, IllegalAccessException {
		if(bindings.containsKey(name) && bindings.get(name) != null)
			return bindings.get(name);
		else
			throw new BindingResolverException("There is no binding for this key name");
	}

	@Override
	public Map<String, Object> getBindings() {
		return bindings;
	}


	@Override
	public Object getBinding() throws BindingResolverException, InstantiationException, IllegalAccessException {
		return getBinding(null);
	}

	@Override
	public IBinding unbind() {
		bindings.remove(null); // remove default value
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
