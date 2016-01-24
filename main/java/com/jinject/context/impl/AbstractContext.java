package com.jinject.context.impl;

import com.jinject.action.api.IActionBinder;
import com.jinject.action.impl.ActionBinder;
import com.jinject.context.api.IContext;
import com.jinject.inject.api.IInjectionBinder;
import com.jinject.inject.api.IInjector;
import com.jinject.inject.impl.InjectionBinder;
import com.jinject.inject.impl.Injector;
import com.jinject.reflect.impl.Reflector;
import com.jinject.view.api.IView;

/**
 * An abstract context based on differents binders : injectionBinder & actionBinder
 * @author Fixe
 *
 */
public abstract class AbstractContext implements IContext {
	protected final IInjector injector;
	protected final InjectionBinder injectionBinder;
	protected final ActionBinder actionBinder;
	
	public AbstractContext() {
		injector = new Injector(new Reflector());
		injectionBinder = new InjectionBinder(injector);
		actionBinder = new ActionBinder(injector, injectionBinder);

		injectionBinder.bind(IInjectionBinder.class).to(injectionBinder).lock();
		injectionBinder.bind(IActionBinder.class).to(actionBinder).lock();
		injectionBinder.bind(IContext.class).to(this).lock();
		
		setupBindings();
		setupActions();
		
		start();
	}

	@Override
	public Object register(Object o) {
		return injectionBinder.register(o);
	}

	/**
	 * The ideal place to puts your bindings
	 */
	public abstract void setupBindings();
	
	/**
	 * The ideal place to puts your actions
	 */
	public abstract void setupActions();

	
	public InjectionBinder getInjectionBinder(){
		return injectionBinder;
	}
	
	public ActionBinder getActionBinder(){
		return actionBinder;
	}

	public void addView(IView view) {
		System.out.println("TODO : addView in AbstractContext");
	}
}
