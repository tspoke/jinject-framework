package com.jinject.context.api;

/**
 * 
 * @author Thibaud Giovannetti
 *
 */
public interface IContext {
	void start();
	
	void destroy();
	
	Object register(Object o);
}
