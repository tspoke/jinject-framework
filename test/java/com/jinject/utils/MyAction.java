package com.jinject.utils;

import com.jinject.action.impl.AbstractAction;
import com.jinject.inject.api.Inject;

public class MyAction extends AbstractAction {

	@Override
	public void execute() {
		System.out.println("My Action execute() called");
		throw new ReachedException();
	}

}
