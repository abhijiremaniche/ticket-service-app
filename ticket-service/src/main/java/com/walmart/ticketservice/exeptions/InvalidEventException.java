package com.walmart.ticketservice.exeptions;

public class InvalidEventException extends RuntimeException {
	
	/**
	 * @author Aremaniche 
	 * This is Runtime Exception class.It represents client request came with invalid event id. 
	 */
	private static final long serialVersionUID = -682189605262600595L;

	public InvalidEventException(String errorMessage) {
		super(errorMessage);
	}


}
