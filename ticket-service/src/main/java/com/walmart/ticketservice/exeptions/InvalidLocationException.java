package com.walmart.ticketservice.exeptions;

public class InvalidLocationException extends RuntimeException {
	
	/**
	 * @author Aremaniche 
	 * This is Runtime Exception class.It represents client request came with invalid location id. 
	 */
	private static final long serialVersionUID = 6811215760638767471L;

	public InvalidLocationException(String errorMessage) {
		super(errorMessage);
	}
}
