package com.jinject.reflect.api;

import com.jinject.bind.api.IBinder;
import com.jinject.bind.exception.BindingResolverException;
import com.jinject.inject.impl.InjectorBindingMapper;

/**
 * A reflector provide methods to reflect classes properly
 * @author Thibaud Giovannetti
 *
 */
public interface IReflector {
	/**
	 * This method create a InjectorBindingMapper for a specific class. It uses reflection to get annoted fields. 
	 * Consider using this method to pre-build all classes who need to be injected to improve performances.
	 * @param clazz
	 * @param binder
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws BindingResolverException
	 */
	InjectorBindingMapper reflectClass(Class<?> clazz, IBinder binder) throws InstantiationException, IllegalAccessException, BindingResolverException;
}
