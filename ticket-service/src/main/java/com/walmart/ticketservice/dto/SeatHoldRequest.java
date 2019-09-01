package com.walmart.ticketservice.dto;
/**
 * @author Aremaniche
 *This is Plain dto class .This is used to store client seat hold request information  .
 */
public class SeatHoldRequest {

	private Long locationId;
	private Long evenlId;
	private Integer numberOfSeats;
	private String userName;

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getEvenlId() {
		return evenlId;
	}

	public void setEvenlId(Long evenlId) {
		this.evenlId = evenlId;
	}

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
