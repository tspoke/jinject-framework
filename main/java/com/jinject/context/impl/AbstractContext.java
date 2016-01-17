package com.jinject.context.impl;

import com.jinject.context.api.IContext;
import com.jinject.inject.impl.InjectionBinder;
import com.jinject.inject.impl.Injector;
import com.jinject.reflect.impl.Reflector;

/**
 * An abstract context based on the injectionBinder system.
 * @author Fixe
 *
 */
public abstract class AbstractContext implements IContext {
	protected final InjectionBinder injectionBinder;
	
	public AbstractContext() {
		injectionBinder = new InjectionBinder(new Injector(new Reflector()));
		injectionBinder.bind(InjectionBinder.class).to(injectionBinder).lock();
		
		start();
		setupBindings();
	}

	@Override
	public Object register(Object o) {
		return injectionBinder.register(o);
	}
	
	/**
	 * The ideal place to puts your bindings
	 */
	public abstract void setupBindings();

	
	public InjectionBinder getInjectionBinder(){
		return injectionBinder;
	}
}
