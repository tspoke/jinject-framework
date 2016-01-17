package com.jinject.inject.exception;

@SuppressWarnings("serial")
public class InjectionException extends RuntimeException {
	private String message;
	
	public InjectionException(String message) {
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
}
