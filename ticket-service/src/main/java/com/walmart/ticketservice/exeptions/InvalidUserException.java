package com.walmart.ticketservice.exeptions;

public class InvalidUserException extends RuntimeException {
	
	/**
	 * @author Aremaniche
	 * This is Runtime Exception class.It represents client request came with invalid user id.
	 */
	private static final long serialVersionUID = 3066091497681775429L;

	public InvalidUserException(String errorMessage) {
		super(errorMessage);
	}

}
