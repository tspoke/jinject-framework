package com.jinject.bind.impl;

import java.util.HashMap;
import java.util.Map;

import com.jinject.bind.api.IBinder;
import com.jinject.bind.api.IBinding;
import com.jinject.bind.exception.BindingResolverException;

public class Binder implements IBinder {
	
	protected Map<Object, IBinding> bindings;
	
	public Binder() {
		bindings = new HashMap<>();
	}

	
	@Override
	public Object getBinding(Object key) throws InstantiationException, IllegalAccessException, BindingResolverException {
		return getBinding(key, null);
	}

	@Override
	public Object getBinding(Object key, Object name) throws InstantiationException, IllegalAccessException, BindingResolverException {
		return resolveBinding(key).getBinding(name);
	}

	@Override
	public IBinding bind(Object key) {
		IBinding binding = resolveBinding(key);
		return binding;
	}
	
	protected IBinding resolveBinding(Object key){
		if(bindings.containsKey(key))
			return bindings.get(key);
		
		IBinding binding = new Binding(key);
		bindings.put(key, binding);
		return binding;
	}
	

	@Override
	public void unbindAll(Object key) {
		resolveBinding(key).unbindAll();
	}

	@Override
	public void unbind(Object key) {
		resolveBinding(key).unbind();
	}

	@Override
	public void unbind(Object key, Object name) {
		resolveBinding(key).unbind(name);
	}

	public Map<Object, IBinding> getBindings() {
		return bindings;
	}
}
