package com.jinject.context.api;

/**
 * 
 * @author Thibaud Giovannetti
 *
 */
public interface IContext {
	void start();
	
	Object register(Object o);
}
