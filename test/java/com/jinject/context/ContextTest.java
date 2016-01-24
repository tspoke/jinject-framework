package com.jinject.context;

import org.junit.Assert;
import org.junit.Test;

import com.jinject.action.impl.ActionBinder;
import com.jinject.bind.exception.BindingResolverException;
import com.jinject.inject.impl.InjectionBinder;
import com.jinject.utils.ContextImpl;
import com.jinject.utils.Controller;
import com.jinject.utils.Events.BinaryTestEvent;
import com.jinject.utils.Events.SimpleTestEvent;
import com.jinject.utils.IModel;
import com.jinject.utils.Model;
import com.jinject.utils.MyView;
import com.jinject.utils.ReachedException;

/**
 * Test cases for context
 * @author Thibaud Giovannetti
 *
 */
public class ContextTest {
	@Test
	public void basic(){
		ContextImpl context = new ContextImpl();
		InjectionBinder injectionBinder = context.getInjectionBinder();
		
		Model model = (Model) injectionBinder.getInstance(IModel.class, "test");
		
		Assert.assertEquals(model.getValue(), 100);
	}
	
	@Test
	public void registerWithContextAndFullInjection(){
		ContextImpl context = new ContextImpl();
		
		Controller control = new Controller();
		context.register(control);
		
		Assert.assertEquals(control.getModel().getOther().getValue(), 0);
	}

	@Test
	public void contextRetrieveEvent() throws InstantiationException, IllegalAccessException, BindingResolverException{
		ContextImpl context = new ContextImpl();
		
		ActionBinder actionBinder = context.getActionBinder();
		BinaryTestEvent event = (BinaryTestEvent) actionBinder.getBinding(BinaryTestEvent.class);
		
		Assert.assertEquals(event.getClass().equals(BinaryTestEvent.class), true);
	}
	
	@Test(expected=ReachedException.class)
	public void contextEventLaunchAction() throws InstantiationException, IllegalAccessException, BindingResolverException{
		ContextImpl context = new ContextImpl();
		
		ActionBinder actionBinder = context.getActionBinder();
		BinaryTestEvent event = (BinaryTestEvent) actionBinder.getBinding(BinaryTestEvent.class);
		
		event.fire("first param", 2);
	}
	
	@Test
	public void view() throws InstantiationException, IllegalAccessException, BindingResolverException{
		ContextImpl context = new ContextImpl();
		InjectionBinder injectionBinder = context.getInjectionBinder();
		injectionBinder.bind(MyView.class).to(MyView.class);
		injectionBinder.bind(SimpleTestEvent.class).to(SimpleTestEvent.class);
		
		MyView view = (MyView) injectionBinder.getInstance(MyView.class);
		
		Assert.assertEquals(view.isReady(), true);
	}
}
