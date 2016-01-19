package com.jinject.event.api;

public interface IEvent {
	/**
	 * Add a listener to this event\n
	 * Avoid creating an anonymous class, consider passing a named one instead to permit removeListener() call.
	 * @param listener
	 */
	void addListener(IListener listener);
	
	/**
	 * Remove a listener to this event
	 * @param listener
	 */
	void removeListener(IListener listener);
	
	/**
	 * Fire the event and notify all listeners
	 */
	void fire(Object... params);
}
