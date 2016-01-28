package com.jinject.action.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
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
import com.jinject.inject.impl.InjectorBindingMapper;

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
	
	public ActionBinder(IInjector injector, Binder binder) {
		this.injector = injector;
		this.bindings = binder.getBindings(); // shared bindings
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
			addListenerOnEvent((IEvent) object);
		
		return resolveBinding(object);
	}
	
	private void addListenerOnEvent(final IEvent eventInstance){
		if(!bindings.containsKey(eventInstance)){
			eventInstance.addListener(new IListener() {
				@Override
				public void execute(Object... params) {
					eventFired(eventInstance, params);
				}
			});
		}
	}
	
	@SuppressWarnings("unchecked")
	private void eventFired(IEvent eventInstance, Object[] params){
		ActionBinding binding = (ActionBinding) resolveBinding(eventInstance.getClass());
		
		// for each param we get its type and create a mapping
		Map<Class<?>, Object> paramsToBind = new HashMap<Class<?>, Object>(params.length * 2);
		for(Object o : params){
			paramsToBind.put(o.getClass(), o);
		}
		
		InjectorBindingMapper mapper = null;
		Map<Field, Object> notBind = null;
		for(Entry<Object, Object> e : binding.getNamedBindings().entrySet()){
			try {
				mapper = injector.getMapperForClass(e.getValue(), this);
				IAction action = null;
				
				if(mapper.isConstructorInjectable())
					action = (IAction) injector.injectConstructor(action, this, mapper.getConstructor(), mapper.getBindingsForConstructor());
				else
					action = ((Class<? extends IAction>) e.getValue()).newInstance();
				
				notBind = injector.injectTypesWithInstances(action, mapper.getBindingsForFields(), paramsToBind);
				injector.injectFields(action, this, notBind, true);
				
				action.execute();
			} 
			catch (IllegalArgumentException | IllegalAccessException | InstantiationException | BindingResolverException | InvocationTargetException | NoSuchMethodException | SecurityException e1) {
				throw new InjectionException("ActionBinder : Cannot cast and inject into : " + e.getValue() + ". \nPrevious error : " + e1.getClass() + " => " + e1.getMessage());
			}
		}
	}
	
	/**
	 * Override default behaviour to use ActionBinding instead
	 */
	@Override
	protected IBinding resolveBinding(Object key) {
		if(bindings.containsKey(key))
			return bindings.get(key);
		
		IBinding binding = new ActionBinding(key);
		bindings.put(key, binding);
		return binding;
	}
}
