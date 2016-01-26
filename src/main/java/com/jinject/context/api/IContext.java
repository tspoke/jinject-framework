package com.jinject.context.api;

import com.jinject.view.api.IView;

/**
 * 
 * @author Thibaud Giovannetti
 *
 */
public interface IContext {
	void start();
	
	Object register(Object o);
	
	void addView(IView view);
}
