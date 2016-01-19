package com.jinject.event.impl;

import com.jinject.event.api.IListener;
import com.jinject.event.exception.EventException;
import com.jinject.event.exception.ListenerException;

public class TernaryEvent<T, U, V> extends AbstractEvent {

	@Override
	public void addListener(IListener listener) {
		if(!(listener instanceof TernaryListener))
			throw new ListenerException("addListener() on a TernaryEvent expect an instance of TernaryListener. Wrong listener given.");
		super.addListener(listener);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void fire(Object... params) {
		if(params.length != 3)
			throw new EventException("Wrong number of params in event dispatching, expected 3 given " + params.length);
		fire((T) params[0], (U) params[1], (V) params[2]);
	}
	
	
	public void fire(T param, U param2, V param3){
		super.fire(param, param2, param3);
	}
}