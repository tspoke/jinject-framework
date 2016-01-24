package com.jinject.utils;

import com.jinject.context.impl.AbstractContext;
import com.jinject.utils.Events.BinaryTestEvent;

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
	public void setupActions() {
		actionBinder.bind(BinaryTestEvent.class).to(MyAction.class);
	}

	@Override
	public void start() {
		
	}

}