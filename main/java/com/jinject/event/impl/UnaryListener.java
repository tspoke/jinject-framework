package com.jinject.event.impl;

import com.jinject.event.api.IListener;
import com.jinject.event.exception.ListenerException;

public abstract class UnaryListener<T> implements IListener {
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Object... params) {
		if(params.length != 1)
			throw new ListenerException("Wrong number of params, expected 1 given " + params.length);
		try {
			execute((T) params[0]);
		}
		catch(ClassCastException e){
			throw new ListenerException("Wrong type of parameters given :" + e.getMessage());
		}
	}
	
	public abstract void execute(T param);
}
