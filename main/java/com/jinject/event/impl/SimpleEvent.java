package com.jinject.event.impl;

public class SimpleEvent extends AbstractEvent {
	
	// To facilitate usage, we permit to use anonymous class instanciation with IListener directly
	/*
	@Override
	public void addListener(IListener listener) {
		if(!(listener instanceof SimpleListener))
			throw new ListenerException("addListener() on a SimpleEvent expect an instance of SimpleListener. Wrong listener given.");
		super.addListener(listener);
	}
	*/
}
