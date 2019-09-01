package com.walmart.ticketservice.dto;
/**
 * @author Aremaniche
 *This is Plain dto class .This is used to send location or venue information to client application .
 */
public class LocationDetails {
	private String locationName;
	private String address;

	 

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
