package com.walmart.ticketservice.exeptions;

/**
	 * @author Aremaniche 
	 * This is Runtime Exception class. Application throws this
	 * exception when client send duplicate request to reserve with same hold id. 
	 *
	 */
public class HoldIdAlreadyUsedForReservationException  extends RuntimeException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4248988614142871916L;
	
	public HoldIdAlreadyUsedForReservationException(String errorMessage) {
		super(errorMessage);
	}

}
