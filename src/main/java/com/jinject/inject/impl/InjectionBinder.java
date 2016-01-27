package com.jinject.inject.impl;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.jinject.bind.api.IBinding;
import com.jinject.bind.exception.BindingResolverException;
import com.jinject.bind.impl.Binder;
import com.jinject.inject.api.IInjectionBinder;
import com.jinject.inject.api.IInjector;

/**
 * InjectionBinder is an implementation based on Binder of IInjectionBinder.
 * It combines the binder system to an injection system.
 * @author Thibaud Giovannetti
 */
public class InjectionBinder extends Binder implements IInjectionBinder {
	private final IInjector injector;
	
	/**
	 * Internal set of values binding already injected
	 */
	private Set<Object> valuesBindingInjected;
	private IBinding lastBinding;
	
	
	public InjectionBinder(IInjector injector) {
		this.injector = injector;
		valuesBindingInjected = new HashSet<>();
	}
	
	public InjectionBinder(IInjector injector, Binder binder) {
		this.injector = injector;
		this.bindings = binder.getBindings(); // shared bindings
	}
	
	@Override
	public IBinding bind(Object key) {		
		lastBinding = super.bind(key);
		// reflect(key); // Improve performance (pre-reflect classes) but this call force to write bindings in the right order... it's really painful :D
		return lastBinding;
	}
	
	/**
	 * Reflect all binding values linked to this binding key
	 * @param binding
	 */
	@SuppressWarnings("unused")
	private void reflectBindings(IBinding binding) throws BindingResolverException{
		if(binding == null)
			return;
		
		// changed !
		for(Entry<Object, Object> b : binding.getBindings().entrySet()){
			Object toReflect = b.getValue();
			if(toReflect instanceof Class)
				reflect((Class<?>) toReflect);
			else
				reflect(toReflect.getClass());
		}
	}
	
	/** 
	 * Reflect a specific class and add it to the cache
	 * @param clazz
	 */
	private void reflect(Class<?> clazz){
		try {
			injector.register(clazz, this);
		}
		catch (InstantiationException | IllegalAccessException | BindingResolverException e) {
			throw new IllegalStateException("The given class leaded to an injection error : " + clazz + ". Initial error : " + e.getMessage());
		}
	}


	@Override
	public Object inject(Object o) {
		if(o instanceof Class)
			throw new IllegalStateException("You can't inject a class type, only instances");
		
		try {
			injector.inject(o, this);
			return o;
		} 
		catch (IllegalArgumentException | IllegalAccessException | InstantiationException | BindingResolverException e) {
			throw new IllegalStateException("Register process failed : " + e);
		}
	}
	
	
	/**
	 * Get a typed instance of the binding.
	 * This version is optimized to avoid multiple injection to value bindings.
	 * TODO 	Provide a mecanism to avoid infinite injection (inject same object in the object itself)
	 */
	@Override
	public Object getInstance(Object key, Object name) {
		Object o = null;
		try {
			o = super.getBinding(key, name);
		} 
		catch (InstantiationException | IllegalAccessException | BindingResolverException e) {
			throw new IllegalStateException("There is no binding for this object. Did you call bind() in your initializer ?", e);
		}
		
		if(!(o instanceof Class) && !valuesBindingInjected.contains(o)){ // is a value (instance) && not already injected
			try {
				o = injector.inject(o, this);
				valuesBindingInjected.add(o);
			}
			catch (InstantiationException | IllegalArgumentException | IllegalAccessException | BindingResolverException e) {
				throw new IllegalStateException("The injection process failed. Did you bind all your objects annoted with @Inject ?", e);
			}
		}
		else if (o instanceof Class){
			try {
				o = injector.inject(o, this); // inject this new instance
			} 
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException | BindingResolverException e) {
				throw new IllegalStateException("The creation and injection of an instance of the type " + key + " failed.", e);
			}
		}
		
		try {
			return o;
		}
		catch(ClassCastException e){
			throw new IllegalStateException("The cast failed, the binder could be inconsistent.", e);
		}
	}

	@Override
	public Object getInstance(Object key) {
		return getInstance(key, null);
	}

}
