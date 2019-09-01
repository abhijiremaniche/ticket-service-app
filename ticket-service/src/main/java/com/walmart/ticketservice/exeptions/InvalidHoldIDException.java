package com.walmart.ticketservice.exeptions;

public class InvalidHoldIDException extends RuntimeException {
	

	/**
	 * @author Aremaniche 
	 * This is Runtime Exception class.It represents client request came with invalid hold seat id. 
	 */
	private static final long serialVersionUID = 765196282304898432L;

	public InvalidHoldIDException(String errorMessage) {
		super(errorMessage);
	}

}
