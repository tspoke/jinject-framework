package com.jinject.action;

import org.junit.Test;

import com.jinject.action.impl.ActionBinder;
import com.jinject.bind.exception.BindingResolverException;
import com.jinject.inject.impl.Injector;
import com.jinject.reflect.impl.Reflector;
import com.jinject.utils.Events.SimpleTestEvent;
import com.jinject.utils.MyAction;
import com.jinject.utils.ReachedException;

public class ActionTest {
	
	@Test(expected=ReachedException.class)
	public void bindEventToAction() throws InstantiationException, IllegalAccessException, BindingResolverException{
		ActionBinder actionBinder = new ActionBinder(new Injector(new Reflector()));
		actionBinder.bind(SimpleTestEvent.class).to(MyAction.class);
		
		SimpleTestEvent event = (SimpleTestEvent) actionBinder.getBinding(SimpleTestEvent.class);
		event.fire();
	}
	
	
}
