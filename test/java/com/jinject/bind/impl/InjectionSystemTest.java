package com.jinject.bind.impl;

import org.junit.Assert;
import org.junit.Test;

import com.jinject.bind.api.IBinder;
import com.jinject.bind.exception.BindingResolverException;
import com.jinject.bind.impl.Binder;
import com.jinject.bind.model.Controller;
import com.jinject.bind.model.IModel;
import com.jinject.bind.model.IOther;
import com.jinject.bind.model.Model;
import com.jinject.bind.model.Other;
import com.jinject.inject.api.IInjector;
import com.jinject.inject.impl.Injector;

public class InjectionSystemTest {
	
	@Test
	public void basicInjection() throws InstantiationException, IllegalArgumentException, IllegalAccessException, BindingResolverException{
		IBinder binder = new Binder();
		IInjector injector = new Injector();

		Model model = new Model();
		model.setValue(155);
		
		Model model2 = new Model();
		model2.setValue(285);

		binder.bind(IModel.class).to(model2);
		binder.bind(IOther.class).to(Other.class);
		binder.bind(IModel.class).to(model).toName("test");

		Controller control = new Controller();
		injector.inject(control, binder);
		Assert.assertEquals(control.model.getValue(), 285);
	}
	
	@Test
	public void fastInjection() throws InstantiationException, IllegalArgumentException, IllegalAccessException, BindingResolverException{
		IBinder binder = new Binder();
		IInjector injector = new Injector();

		Model model = new Model();
		model.setValue(155);

		binder.bind(IOther.class).to(Other.class);
		binder.bind(IModel.class).to(model);
		
		Controller control = new Controller();
		Controller control2 = new Controller();
		// slow injection to first Controller and creation of mapping
		injector.inject(control, binder);
		// fast injection to second controller using previous mapping stored in the injector
		injector.inject(control2);
		
		Assert.assertEquals(control2.model.getValue(), 155);
	}
}
