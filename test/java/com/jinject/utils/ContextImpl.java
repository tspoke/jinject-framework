package com.jinject.utils;

import com.jinject.context.impl.AbstractContext;

public class ContextImpl extends AbstractContext {
	
	@Override
	public void setupBindings() {
		injectionBinder.bind(IOther.class).to(Other.class);
		injectionBinder.bind(IModel.class).to(Model.class);
		
		Model model = new Model();
		model.setValue(100);
		
		injectionBinder.bind(IModel.class).to(model).toName("test");
	}

	@Override
	public void start() {
		
	}

	@Override
	public void destroy() {
		
	}
}