package com.walmart.ticketservice.dto;
/**
 * @author Aremaniche
 *This is Plain dto class .This is used to store client Reservation Request information  .
 */
public class ReservationRequest {
	private Long seatHoldId;
	private String userName;

	public Long getSeatHoldId() {
		return seatHoldId;
	}

	public void setSeatHoldId(Long seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
