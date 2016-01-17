package com.jinject;

import org.junit.Assert;

import com.jinject.bind.api.IBinder;
import com.jinject.bind.exception.BindingResolverException;
import com.jinject.bind.impl.Binder;
import com.jinject.bind.model.Controller;
import com.jinject.bind.model.IModel;
import com.jinject.bind.model.IOther;
import com.jinject.bind.model.Model;
import com.jinject.bind.model.Other;
import com.jinject.inject.api.IInjector;
import com.jinject.inject.impl.InjectionBinder;
import com.jinject.inject.impl.Injector;

public class Main {

	public static void main(String[] args) throws InstantiationException, IllegalArgumentException, IllegalAccessException, BindingResolverException {
		
		IInjector injector = new Injector();
		InjectionBinder injectionBinder = new InjectionBinder(injector);
		
		injectionBinder.bind(IOther.class).to(Other.class);
		injectionBinder.bind(IModel.class).to(Model.class);
		
		Model clazz = (Model) injectionBinder.getInstance(IModel.class);
		
		Assert.assertEquals(clazz.getValue(), 0);
		
		
		// ********
		//IInjector injector = new Injector();
		/*
		InjectionBinder injectionBinder = new InjectionBinder(injector);

		Other other = new Other();
		other.setValue(999);
		
		Model model = new Model();
		model.setValue(155);
		
		Model model2 = new Model();
		model2.setValue(285);
		
		injectionBinder.bind(IOther.class).to(Other.class);
		injectionBinder.bind(IModel.class).to(Model.class);

		Controller control = new Controller();
		Controller control2 = new Controller();
		
		injector.inject(control, injectionBinder);
		injector.inject(control2);

		System.out.println("**********************************************************");
		System.out.println(control.model.getOther());
		System.out.println(control2.model.getOther());
		System.out.println(control.model.getOther().equals(control2.model.getOther()));
		*/
	}
}
