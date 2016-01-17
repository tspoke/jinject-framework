package com.jinject.inject.impl;

import java.util.HashSet;
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
 *
 */
public class InjectionBinder extends Binder implements IInjectionBinder {
	private final IInjector injector;
	
	/**
	 * Internal set of values binding already injected
	 */
	private Set<Object> valuesBindingInjected; 
	
	
	public InjectionBinder(IInjector injector) {
		this.injector = injector;
		valuesBindingInjected = new HashSet<>();
	}
	
	
	@Override
	public IBinding bind(Class<?> key) {
		IBinding binding = super.bind(key);
		try {
			injector.reflectClass(key, this);
		}
		catch (InstantiationException | IllegalAccessException | BindingResolverException e) {
			throw new IllegalStateException("Error in reflection : the class you gave to bind() is not valid", e);
		}
		return binding;
	}
	
	
	/**
	 * Get a typed instance of the binding.
	 * This version is optimized to avoid multiple injection to value bindings.
	 * TODO 	Provide a mecanism to avoid infinite injection (inject same object in the object itself)
	 */
	@Override
	public <T> T getInstance(Class<T> key, String name) {
		Object o = null;
		try {
			o = super.getBinding(key, name);
		} 
		catch (InstantiationException | IllegalAccessException | BindingResolverException e) {
			throw new IllegalStateException("There is no binding for this object. Did you call bind() in your initializer ?", e);
		}
		
		if(!(o instanceof Class) && !valuesBindingInjected.contains(o)){ // is a value && not already injected
			try {
				injector.inject(o, this);
			} 
			catch (InstantiationException | IllegalArgumentException | IllegalAccessException | BindingResolverException e) {
				throw new IllegalStateException("The injection process failed. Did you bind all your objects annoted with @Inject ?", e);
			} 
			
			valuesBindingInjected.add(o);
		}
		else {
			try {
				o = ((Class<?>) o).newInstance();
			} 
			catch (InstantiationException | IllegalAccessException e) {
				throw new IllegalStateException("The creation of an instance of the type " + key + " failed.", e);
			}
		}
		
		try {
			return (T) o;
		}
		catch(ClassCastException e){
			throw new IllegalStateException("The cast failed, the binder could be inconsistent.", e);
		}
	}

	@Override
	public <T> T getInstance(Class<T> key) {
		return getInstance(key, null);
	}
}
