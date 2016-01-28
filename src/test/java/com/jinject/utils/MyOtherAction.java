package com.jinject.utils;

import org.apache.log4j.Logger;

import com.jinject.action.impl.AbstractAction;
import com.jinject.inject.api.Inject;

public class MyOtherAction extends AbstractAction {
	private static final Logger logger = Logger.getLogger(MyOtherAction.class);
	
	@Inject
	private IOther other;
	
	@Inject
	private String myString;
	
	@Override
	public void execute() {
		logger.info("My MyOtherAction execute() called ");
		logger.info("myString : " + myString);
		logger.info("IOther value : " + other.getValue());
		throw new ReachedException();
	}

}
