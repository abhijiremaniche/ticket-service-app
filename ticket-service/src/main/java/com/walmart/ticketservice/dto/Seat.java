package com.walmart.ticketservice.dto;
/**
 * @author Aremaniche
 *This is Plain dto class .This is used to send seat information to client application .
 */
public class Seat {
	@Override
	public String toString() {
		return "[" + seatLocation + "]";
	}

	private String seatLocation;

	public String getSeatLocation() {
		return seatLocation;
	}

	public void setSeatLocation(String seatLocation) {
		this.seatLocation = seatLocation;
	}

}
