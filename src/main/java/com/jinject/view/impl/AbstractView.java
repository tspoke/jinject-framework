package com.jinject.view.impl;

import com.jinject.context.api.IContext;
import com.jinject.view.api.IView;

public class AbstractView implements IView {
	private IContext context;

	public AbstractView() {
		// registerWithContext();
	}
	
	final protected void registerWithContext(){
		if(context == null)
			throw new IllegalStateException("The context is not set in view");
		context.addView(this);
	}
}
