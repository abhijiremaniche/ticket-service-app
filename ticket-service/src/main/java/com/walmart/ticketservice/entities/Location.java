package com.walmart.ticketservice.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * @author Aremaniche
 * The persistent class for the LOCATION database table.
 * 
 */
@Entity
@NamedQuery(name="Location.findAll", query="SELECT l FROM Location l")
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LOCATION_LOCATIONID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOCATION_LOCATIONID_GENERATOR")
	@Column(name="LOCATION_ID")
	private Long locationId;

	@Column(name= "LOCATION_ADDRESS")
	private String locationAddress;

	@Column(name="LOCATION_NM")
	private String locationNm;
	@Version
	@Column(name="ROW_VERSION")
	private Integer rowVersion;

	//bi-directional many-to-one association to AvailableSeat
	@OneToMany(mappedBy="location")
	private List<AvailableSeat> availableSeats;

	//bi-directional many-to-one association to LocationSeatHold
	@OneToMany(mappedBy="location")
	private List<LocationSeatHold> locationSeatHolds;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="location")
	private List<Event> events;

	public Location() {
	}

	public Long getLocationId() {
		return this.locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

 

	public String getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	public String getLocationNm() {
		return this.locationNm;
	}

	public void setLocationNm(String locationNm) {
		this.locationNm = locationNm;
	}

	public Integer getRowVersion() {
		return this.rowVersion;
	}

	public void setRowVersion(Integer rowVersion) {
		this.rowVersion = rowVersion;
	}

	public List<AvailableSeat> getAvailableSeats() {
		return this.availableSeats;
	}

	public void setAvailableSeats(List<AvailableSeat> availableSeats) {
		this.availableSeats = availableSeats;
	}

	public AvailableSeat addAvailableSeat(AvailableSeat availableSeat) {
		getAvailableSeats().add(availableSeat);
		availableSeat.setLocation(this);

		return availableSeat;
	}

	public AvailableSeat removeAvailableSeat(AvailableSeat availableSeat) {
		getAvailableSeats().remove(availableSeat);
		availableSeat.setLocation(null);

		return availableSeat;
	}

	public List<LocationSeatHold> getLocationSeatHolds() {
		return this.locationSeatHolds;
	}

	public void setLocationSeatHolds(List<LocationSeatHold> locationSeatHolds) {
		this.locationSeatHolds = locationSeatHolds;
	}

	public LocationSeatHold addLocationSeatHold(LocationSeatHold locationSeatHold) {
		getLocationSeatHolds().add(locationSeatHold);
		locationSeatHold.setLocation(this);

		return locationSeatHold;
	}

	public LocationSeatHold removeLocationSeatHold(LocationSeatHold locationSeatHold) {
		getLocationSeatHolds().remove(locationSeatHold);
		locationSeatHold.setLocation(null);

		return locationSeatHold;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Event addEvent(Event event) {
		getEvents().add(event);
		event.setLocation(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setLocation(null);

		return event;
	}

}
