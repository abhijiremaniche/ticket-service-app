package com.walmart.ticketservice.dto;
/**
 * @author Aremaniche
 *This is Plain dto class .This is used to send Seat Hold information to client application .
 */
import java.util.ArrayList;
import java.util.List;

public class SeatHold {
	private Long seatHoldId;
	private String username;
	private List<Seat> seats = new ArrayList<>();
	private EventDetails eventDetails;
	private LocationDetails locationDetails;

	public Long getSeatHoldId() {
		return seatHoldId;
	}

	public void setSeatHoldId(Long seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public EventDetails getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(EventDetails eventDetails) {
		this.eventDetails = eventDetails;
	}

	public LocationDetails getLocationDetails() {
		return locationDetails;
	}

	public void setLocationDetails(LocationDetails locationDetails) {
		this.locationDetails = locationDetails;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
