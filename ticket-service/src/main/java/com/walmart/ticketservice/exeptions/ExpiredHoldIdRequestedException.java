package com.walmart.ticketservice.exeptions;

public class ExpiredHoldIdRequestedException extends RuntimeException {

	/**
	 * @author Aremaniche 
	 * This is Runtime Exception class.Application throws this
	 * exception when client send request to reserve with hold id and that
	 * seat hold id is expired.
	 */
	private static final long serialVersionUID = 4230487934413097640L;

	public ExpiredHoldIdRequestedException(String errorMessage) {
		super(errorMessage);
	}

}
