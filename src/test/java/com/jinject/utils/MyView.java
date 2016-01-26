package com.jinject.utils;

import com.jinject.context.api.IContext;
import com.jinject.inject.api.InjectConstructor;
import com.jinject.utils.Events.SimpleTestEvent;
import com.jinject.view.impl.AbstractView;

public class MyView extends AbstractView {
	private SimpleTestEvent event;
	
	@InjectConstructor
	public MyView(IContext context, SimpleTestEvent event) {
		super(context);
		this.event = event;
		System.out.println("Event value after injection : " + this.event);
	}
	
	public boolean isReady(){
		System.out.println("Event value after isReady : " + event);
		return true;
	}
}
