package com.jinject.inject.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Injector will try to inject values to the constructor annoted with it
 * @author Thibaud Giovannetti
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface InjectConstructor {
	// TODO 	Permit to bind by name parameters
}
