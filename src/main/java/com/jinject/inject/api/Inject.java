package com.jinject.inject.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Injector will try to inject a value to the field annoted with this annotation
 * @author Thibaud Giovannetti
 * @param  value 	Injection specific name, by default null
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Inject {
	String value() default "";
}
