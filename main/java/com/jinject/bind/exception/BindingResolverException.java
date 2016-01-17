package com.jinject.bind.exception;

@SuppressWarnings("serial")
public class BindingResolverException extends Exception {
	private String message;
	
	public BindingResolverException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
