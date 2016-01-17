package com.jinject;

import com.jinject.bind.exception.BindingResolverException;
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
	}
}
