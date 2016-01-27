package com.jinject.bind.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.jinject.bind.api.IBinding;
import com.jinject.bind.exception.BindingResolverException;

public class Binding implements IBinding {
	private Object key;
	private boolean locked = false;
	private Map<Object, Object> bindings; // name, value
	private Object temporary;
	
	public Binding(Object k) {
		key = k;
		bindings = new HashMap<>();
	}

	@Override
	public IBinding to(Object o) {
		if(locked && bindings.size() > 0)
			throw new ExceptionInInitializerError("This binding is locked !");
		
		if(temporary != null)
			bindings.put(null, temporary);
		else if(!bindings.containsKey(null))
			bindings.put(null, o);
		
		temporary = o;
		return this;
	}


	@Override
	public IBinding toName(Object name) {
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
	public Object getBinding(Object name) throws BindingResolverException, InstantiationException, IllegalAccessException {
		if(bindings.containsKey(name) && bindings.get(name) != null)
			return bindings.get(name);
		else { 
			return key;// should I ?
			//throw new BindingResolverException("There is no binding for this key name");
		}
	}

	@Override
	public Map<Object, Object> getBindings() {
		return bindings;
	}
	
	// TODO 	optimize this code
	@Override
	public Map<Object, Object> getNamedBindings() {
		Map<Object, Object> namedOnly = new HashMap<Object, Object>(bindings.size() - 1);
		
		for(Entry<Object, Object> e : bindings.entrySet()){
			if(e.getKey() != null)
				namedOnly.put(e.getKey(), e.getValue());
		}
		
		return namedOnly;
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
	public IBinding unbind(Object name) {
		bindings.remove(name);
		return this;
	}

	@Override
	public IBinding unbindAll() {
		bindings.clear();
		return this;
	}
}
