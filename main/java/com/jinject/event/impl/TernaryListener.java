package com.jinject.event.impl;

import com.jinject.event.api.IListener;
import com.jinject.event.exception.ListenerException;

public abstract class TernaryListener<T, U, V> implements IListener {
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Object... params) {
		if(params.length != 3)
			throw new ListenerException("Wrong number of params, expected 3 given " + params.length);
		try {
			execute((T) params[0], (U) params[1], (V) params[2]);
		}
		catch(ClassCastException e){
			throw new ListenerException("Wrong type of parameters given :" + e.getMessage());
		}
	}
	
	public abstract void execute(T param, U param2, V param3);
}
