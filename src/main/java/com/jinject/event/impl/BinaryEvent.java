package com.jinject.event.impl;

import com.jinject.event.exception.EventException;

public class BinaryEvent<T, U> extends AbstractEvent {
	
	/*
	@Override
	public void addListener(IListener listener) {
		if(!(listener instanceof BinaryListener) && !(listener instanceof SimpleListener))
			throw new ListenerException("addListener() on a BinaryEvent expect an instance of BinaryListener, UnaryListener or SimpleListener. Wrong listener given.");
		super.addListener(listener);
	}
	*/
	
	@SuppressWarnings("unchecked")
	@Override
	public void fire(Object... params) {
		if(params.length != 2)
			throw new EventException("Wrong number of params in event dispatching, expected 2 given " + params.length);
		
		fire((T) params[0], (U) params[1]);
	}
	
	public void fire(T param, U param2){
		System.out.println(param);
		System.out.println("=>" + param2);
		super.fire(param, param2);
	}
}