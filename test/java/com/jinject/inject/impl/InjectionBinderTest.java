package com.jinject.inject.impl;

import org.junit.Assert;
import org.junit.Test;

import com.jinject.bind.exception.BindingResolverException;
import com.jinject.inject.api.IInjector;
import com.jinject.reflect.impl.Reflector;
import com.jinject.utils.Controller;
import com.jinject.utils.IModel;
import com.jinject.utils.IOther;
import com.jinject.utils.Model;
import com.jinject.utils.Other;

public class InjectionBinderTest {
	
	@Test
	public void differentReferenceOnInjectedObject() throws InstantiationException, IllegalArgumentException, IllegalAccessException, BindingResolverException{
		IInjector injector = new Injector(new Reflector());
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
		IInjector injector = new Injector(new Reflector());
		InjectionBinder injectionBinder = new InjectionBinder(injector);
		
		injectionBinder.bind(IOther.class).to(Other.class);
		injectionBinder.bind(IModel.class).to(Model.class);
		
		IModel model = injectionBinder.getInstance(IModel.class);
		
		Assert.assertEquals(model.getValue(), 0);
	}
	
	@Test
	public void bindInvertDependencies(){
		IInjector injector = new Injector(new Reflector());
		InjectionBinder injectionBinder = new InjectionBinder(injector);

		injectionBinder.bind(IOther.class).to(Other.class);
		injectionBinder.bind(IModel.class).to(Model.class);
		IModel model = injectionBinder.getInstance(IModel.class);

		Assert.assertEquals(model.getValue(), 0);
		Assert.assertEquals(model.getOther().getValue(), 0);
	}
	
	@Test(expected=IllegalStateException.class)
	public void unbind(){
		IInjector injector = new Injector(new Reflector());
		InjectionBinder injectionBinder = new InjectionBinder(injector);
		
		injectionBinder.bind(IOther.class).to(Other.class);
		injectionBinder.unbind(IOther.class);
		
		injectionBinder.getInstance(IOther.class); // exception
	}
	
	@Test(expected=IllegalStateException.class)
	public void unbindByName(){
		IInjector injector = new Injector(new Reflector());
		InjectionBinder injectionBinder = new InjectionBinder(injector);
		
		injectionBinder.bind(IOther.class).to(Other.class);
		injectionBinder.bind(IModel.class).to(Model.class);
		injectionBinder.bind(IModel.class).to(Model.class).toName("test");
		
		injectionBinder.unbind(IModel.class, "test");
		
		injectionBinder.getInstance(IModel.class, "test");
	}
}
