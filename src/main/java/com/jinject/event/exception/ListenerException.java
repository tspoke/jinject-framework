package com.jinject.event.exception;

/**
 * 
 * @author Thibaud Giovannetti
 *
 */
@SuppressWarnings("serial")
public class ListenerException extends RuntimeException {
	private String message;
	
	public ListenerException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}

