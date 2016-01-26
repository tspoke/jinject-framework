package com.jinject.context.api;

/**
 * 
 * @author Thibaud Giovannetti
 *
 */
public interface IContext {
	void start();
	
	Object inject(Object o);
	
	/**
	 * This method bind a view to itself. It's a syntaxic sugar for injectionBinder.bind(view.class).to(view.class)
	 * @param view
	 */
	void addView(Object view);
}
