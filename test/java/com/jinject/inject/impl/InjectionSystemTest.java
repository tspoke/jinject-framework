package com.jinject.inject.impl;

import org.junit.Assert;
import org.junit.Test;

import com.jinject.bind.api.IBinder;
import com.jinject.bind.exception.BindingResolverException;
import com.jinject.bind.impl.Binder;
import com.jinject.inject.api.IInjector;
import com.jinject.reflect.impl.Reflector;
import com.jinject.utils.Controller;
import com.jinject.utils.IModel;
import com.jinject.utils.IOther;
import com.jinject.utils.Model;
import com.jinject.utils.Other;

public class InjectionSystemTest {
	
	@Test
	public void basicInjection() throws InstantiationException, IllegalArgumentException, IllegalAccessException, BindingResolverException{
		IBinder binder = new Binder();
		IInjector injector = new Injector(new Reflector());

		Model model = new Model();
		model.setValue(155);
		
		Model model2 = new Model();
		model2.setValue(285);

		binder.bind(IModel.class).to(model2);
		binder.bind(IOther.class).to(Other.class);
		binder.bind(IModel.class).to(model).toName("test");

		Controller control = new Controller();
		injector.inject(control, binder);
		Assert.assertEquals(control.getModel().getValue(), 285);
	}
	
	@Test
	public void fastInjection() throws InstantiationException, IllegalArgumentException, IllegalAccessException, BindingResolverException{
		IBinder binder = new Binder();
		IInjector injector = new Injector(new Reflector());

		Model model = new Model();
		model.setValue(155);

		binder.bind(IOther.class).to(Other.class);
		binder.bind(IModel.class).to(model);
		
		Controller control = new Controller();
		Controller control2 = new Controller();
		// slow injection to first Controller and creation of mapping
		injector.inject(control, binder);
		// fast injection to second controller using previous mapping stored in the injector
		injector.inject(control2, binder);
		
		Assert.assertEquals(control2.getModel().getValue(), 155);
	}
	
	@Test
	public void injectionByConstructor() throws InstantiationException, IllegalArgumentException, IllegalAccessException, BindingResolverException{
		IBinder binder = new Binder();
		IInjector injector = new Injector(new Reflector());

		Model model = new Model();
		model.setValue(155);

		binder.bind(IOther.class).to(Other.class);
		binder.bind(IModel.class).to(model);
		
		Controller control = new Controller();
		Controller control2 = new Controller();
		// slow injection to first Controller and creation of mapping
		injector.inject(control, binder);
		// fast injection to second controller using previous mapping stored in the injector
		injector.inject(control2, binder);
		
		Assert.assertEquals(control2.getModel().getValue(), 155);
	}
}
