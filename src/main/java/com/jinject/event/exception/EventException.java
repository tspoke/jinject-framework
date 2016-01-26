package com.jinject.event.exception;

/**
 * 
 * @author Thibaud Giovannetti
 *
 */
@SuppressWarnings("serial")
public class EventException extends RuntimeException {
	private String message;
	
	public EventException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
