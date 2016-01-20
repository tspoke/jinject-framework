package com.jinject.action.impl;

import java.util.Map.Entry;

import com.jinject.action.api.IAction;
import com.jinject.action.api.IActionBinder;
import com.jinject.bind.api.IBinding;
import com.jinject.bind.exception.BindingResolverException;
import com.jinject.bind.impl.Binder;
import com.jinject.event.api.IEvent;
import com.jinject.event.api.IListener;
import com.jinject.inject.api.IInjector;
import com.jinject.inject.exception.InjectionException;

/**
 * The ActionBinder is a special implementation of a binder who is designed to bind Event to Actions.
 * @author Thibaud Giovannetti
 *
 */
public class ActionBinder extends Binder implements IActionBinder {
	private final IInjector injector;
	
	
	public ActionBinder(IInjector injector) {
		this.injector = injector;
	}
	
	@Override
	public IBinding bind(Object object) {
		if((object instanceof Class && object.getClass().isAssignableFrom(IEvent.class)) || !(object instanceof Class) && object instanceof IEvent)
			throw new IllegalStateException("ActionBinder can only bind Event, wrong class given : " + object);
		
		if(object instanceof Class && !bindings.containsKey(object)){
			try {
				@SuppressWarnings("unchecked")
				IEvent uniqueInstance = ((Class<IEvent>) object).newInstance();
				IBinding eventBinding = super.bind(object);
				eventBinding.getBindings().put(null, uniqueInstance); // it's a ActionBinding, some limitations requires this
				addListenerOnEvent(uniqueInstance);
			}
			catch (InstantiationException | IllegalAccessException e) {
				throw new IllegalStateException("ActionBinder can't instantiate and inject this object (is it a IEvent ?) : " + object);
			}
		}
		else if(!(object instanceof Class) && !bindings.containsKey(object))
			addListenerOnEvent(object);
		
		return resolveBinding(object);
	}
	
	private void addListenerOnEvent(final Object eventInstance){
		if(!bindings.containsKey(eventInstance)){
			((IEvent) eventInstance).addListener(new IListener() {
				@Override
				public void execute(Object... params) {
					eventFired(eventInstance);
				}
			});
		}
	}
	
	@SuppressWarnings("unchecked")
	private void eventFired(Object eventInstance){
		ActionBinding binding = (ActionBinding) resolveBinding(eventInstance.getClass());
		for(Entry<Object, Object> e : binding.getNamedBindings().entrySet()){
			try {
				IAction action = ((Class<? extends IAction>) e.getValue()).newInstance();
				injector.inject(action, this);
				action.execute();
			} 
			catch (IllegalArgumentException | IllegalAccessException | InstantiationException | BindingResolverException e1) {
				throw new InjectionException("Cannot cast and inject into : " + e.getValue() + ". Initial error : " + e1.getMessage());
			}
		}
	}
	
	@Override
	protected IBinding resolveBinding(Object key) {
		if(bindings.containsKey(key))
			return bindings.get(key);
		
		IBinding binding = new ActionBinding(key);
		bindings.put(key, binding);
		return binding;
	}
}
