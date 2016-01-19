package com.jinject.event.api;

/**
 * Interface to passing callbacks in methods
 * @author Thibaud Giovannetti
 *
 */
public interface IListener {
	void execute (Object... params);
}
