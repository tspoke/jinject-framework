package com.jinject.utils;

import com.jinject.action.impl.AbstractAction;
import com.jinject.inject.api.Inject;

public class MyAction extends AbstractAction {
	
	@Inject
	public IModel model;

	@Override
	public void execute() {
		System.out.println("MyAction executed : " + model);
	}
	
}
