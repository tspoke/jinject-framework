package com.jinject;

import com.jinject.inject.impl.InjectionBinder;
import com.jinject.utils.ContextImpl;
import com.jinject.utils.Other;

public class Main {

	public static void main(String[] args) {
		ContextImpl context = new ContextImpl();
		InjectionBinder injectionBinder = context.getInjectionBinder();
	}
}
