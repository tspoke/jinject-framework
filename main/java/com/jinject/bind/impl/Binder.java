package com.jinject.bind.impl;

import java.util.HashMap;
import java.util.Map;

import com.jinject.bind.api.IBinder;
import com.jinject.bind.api.IBinding;
import com.jinject.bind.exception.BindingResolverException;

public class Binder implements IBinder {
	
	protected Map<Class<?>, IBinding> bindings;
	
	public Binder() {
		bindings = new HashMap<>();
	}
	
	@Override
	public Object getBinding(Class<?> key) throws InstantiationException, IllegalAccessException, BindingResolverException {
		return getBinding(key, null);
	}

	@Override
	public Object getBinding(Class<?> key, String name) throws InstantiationException, IllegalAccessException, BindingResolverException {
		return resolveBinding(key).getBinding(name);
	}

	@Override
	public IBinding bind(Class<?> key) {
		IBinding binding = resolveBinding(key);
		return binding;
	}
	
	private IBinding resolveBinding(Class<?> key){
		if(bindings.containsKey(key))
			return bindings.get(key);
		
		IBinding binding = new Binding(key);
		bindings.put(key, binding);
		return binding;
	}
	

	@Override
	public void unbindAll(Class<?> key) {
		resolveBinding(key).unbindAll();
	}

	@Override
	public void unbind(Class<?> key) {
		resolveBinding(key).unbind();
	}

	@Override
	public void unbind(Class<?> key, String name) {
		resolveBinding(key).unbind(name);
	}
}
