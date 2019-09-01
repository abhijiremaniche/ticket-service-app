package com.walmart.ticketservice.exeptions;

public class NotEnoughSeatsAvailableToHoldException extends RuntimeException {

	/**
	 * @author Aremaniche
	 *This is Runtime Exception class.This exception is thrown when client requests hold for specific number of seats
	 *and those many seats are not available.
	 */
	private static final long serialVersionUID = 2338667034275095218L;

	public NotEnoughSeatsAvailableToHoldException(String errorMessage) {
		super(errorMessage);
	}

}
