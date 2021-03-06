package com.jinject.utils;

import org.apache.log4j.Logger;

import com.jinject.action.impl.AbstractAction;
import com.jinject.inject.api.Inject;

public class MyAction extends AbstractAction {
	private static final Logger logger = Logger.getLogger(MyAction.class);

	@Inject
	private Integer myInt;

	
	@Inject
	private String myString;
	
	@Override
	public void execute() {
		logger.info("My Action execute() called ");
		logger.info("MyInt : " + myInt);
		logger.info("myString : " + myString);
		throw new ReachedException();
	}

}
