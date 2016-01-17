package com.jinject.context;

import org.junit.Assert;
import org.junit.Test;

import com.jinject.inject.impl.InjectionBinder;
import com.jinject.utils.ContextImpl;
import com.jinject.utils.Controller;
import com.jinject.utils.IModel;
import com.jinject.utils.Model;

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
		
		Assert.assertEquals(control.model.getOther().getValue(), 0);
	}
}
