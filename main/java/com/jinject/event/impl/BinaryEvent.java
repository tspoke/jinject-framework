package com.jinject.event.impl;

import com.jinject.event.api.IListener;
import com.jinject.event.exception.EventException;
import com.jinject.event.exception.ListenerException;

public class BinaryEvent<T, U> extends AbstractEvent {
	
	@Override
	public void addListener(IListener listener) {
		if(!(listener instanceof BinaryListener))
			throw new ListenerException("addListener() on a BinaryEvent expect an instance of BinaryListener. Wrong listener given.");
		super.addListener(listener);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void fire(Object... params) {
		if(params.length != 2)
			throw new EventException("Wrong number of params in event dispatching, expected 2 given " + params.length);
		fire((T) params[0], (U) params[1]);
	}
	
	public void fire(T param, U param2){
		super.fire(param, param2);
	}
}