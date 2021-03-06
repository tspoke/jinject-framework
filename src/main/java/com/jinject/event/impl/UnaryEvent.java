package com.jinject.event.impl;

import com.jinject.event.exception.EventException;

public class UnaryEvent<T> extends AbstractEvent {

	/*
	@Override
	public void addListener(IListener listener) {
		if(!(listener instanceof UnaryListener) && !(listener instanceof SimpleListener))
			throw new ListenerException("addListener() on a UnaryEvent expect an instance of UnaryListener or SimpleListener. Wrong listener given.");
		
		super.addListener(listener);
	}
	*/
	
	@SuppressWarnings("unchecked")
	@Override
	public void fire(Object... params) {
		if(params.length != 1)
			throw new EventException("Wrong number of params in event dispatching, expected 1 given " + params.length);
		fire((T) params[0]);
	}
	
	public void fire(T param){
		super.fire(param);
	}
}
