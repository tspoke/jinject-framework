package com.jinject.bind.impl;

import org.junit.Assert;
import org.junit.Test;

import com.jinject.bind.exception.BindingResolverException;
import com.jinject.bind.model.Controller;
import com.jinject.bind.model.IModel;
import com.jinject.bind.model.IOther;
import com.jinject.bind.model.Model;
import com.jinject.bind.model.Other;
import com.jinject.inject.api.IInjector;
import com.jinject.inject.impl.InjectionBinder;
import com.jinject.inject.impl.Injector;

public class InjectionBinderTest {
	
	@Test
	public void differentReferenceOnInjectedObject() throws InstantiationException, IllegalArgumentException, IllegalAccessException, BindingResolverException{
		IInjector injector = new Injector();
		InjectionBinder injectionBinder = new InjectionBinder(injector);
		
		injectionBinder.bind(IOther.class).to(Other.class);
		injectionBinder.bind(IModel.class).to(Model.class);

		Controller control = new Controller();
		Controller control2 = new Controller();
		
		injector.inject(control, injectionBinder);
		injector.inject(control2);
		
		Assert.assertEquals(control.model.getOther().equals(control2.model.getOther()), false);
	}
	
	@Test
	public void bind(){
		IInjector injector = new Injector();
		InjectionBinder injectionBinder = new InjectionBinder(injector);
		
		injectionBinder.bind(IOther.class).to(Other.class);
		injectionBinder.bind(IModel.class).to(Model.class);
		
		IModel model = injectionBinder.getInstance(IModel.class);
		
		Assert.assertEquals(model.getValue(), 0);
	}
	
	@Test
	public void bindInvertDependencies(){
		IInjector injector = new Injector();
		InjectionBinder injectionBinder = new InjectionBinder(injector);

		injectionBinder.bind(IModel.class).to(Model.class);
		injectionBinder.bind(IOther.class).to(Other.class);
		IModel model = injectionBinder.getInstance(IModel.class);
		
		Assert.assertEquals(model.getValue(), 0);
	}
	
	@Test(expected=IllegalStateException.class)
	public void unbind(){
		IInjector injector = new Injector();
		InjectionBinder injectionBinder = new InjectionBinder(injector);
		
		injectionBinder.bind(IOther.class).to(Other.class);
		injectionBinder.unbind(IOther.class);
		
		injectionBinder.getInstance(IOther.class); // exception
	}
	
	@Test(expected=IllegalStateException.class)
	public void unbindByName(){
		IInjector injector = new Injector();
		InjectionBinder injectionBinder = new InjectionBinder(injector);
		
		injectionBinder.bind(IOther.class).to(Other.class);
		injectionBinder.bind(IModel.class).to(Model.class);
		injectionBinder.bind(IModel.class).to(Model.class).toName("test");
		
		injectionBinder.unbind(IModel.class, "test");
		
		injectionBinder.getInstance(IModel.class, "test");
	}
}
