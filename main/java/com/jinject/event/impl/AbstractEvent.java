package com.jinject.event.impl;

import java.util.HashSet;
import java.util.Set;

import com.jinject.event.api.IEvent;
import com.jinject.event.api.IListener;

public abstract class AbstractEvent implements IEvent {
	protected Set<IListener> listeners = new HashSet<IListener>(10);
	
	
	@Override
	public void addListener(IListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(IListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void fire(Object...params) {
		for(IListener listener : listeners)
			listener.execute(params);
	}

}
