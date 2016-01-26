package com.jinject.utils;

import com.jinject.inject.api.Inject;

public class Controller {
	@Inject
	private IModel model;
	
	public IModel getModel(){
		return model;
	}
}
