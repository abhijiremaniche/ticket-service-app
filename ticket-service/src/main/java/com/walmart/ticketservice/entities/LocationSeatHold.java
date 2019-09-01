package com.walmart.ticketservice.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Aremaniche
 * The persistent class for the LOCATION_SEAT_HOLD database table.
 * 
 */
@Entity
@Table(name = "LOCATION_SEAT_HOLD")
@NamedQuery(name = "LocationSeatHold.findAll", query = "SELECT l FROM LocationSeatHold l")
public class LocationSeatHold implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LocationSeatHoldPK id;

	@Column(name = "EXPIRATION_TIMESTAMP")
	private Timestamp expirationTimestamp;
	@Version
	@Column(name = "ROW_VERSION")
	private Integer rowVersion;

	// bi-directional many-to-one association to AvailableSeat
	@ManyToOne
	@JoinColumn(name = "SEAT_ID" , insertable = false, updatable = false)
	private AvailableSeat availableSeat;

	// bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name = "LOCATION_ID" , insertable = false, updatable = false)
	private Location location;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "USER_ID" , insertable = false, updatable = false)
	private User user;

	// bi-directional many-to-one association to LocationSeatReservation
	@OneToMany(mappedBy = "locationSeatHold"  )
	private List<LocationSeatReservation> locationSeatReservations;

	// bi-directional many-to-one association to Event
	@ManyToOne
	@JoinColumn(name = "EVENT_ID" , insertable = false, updatable = false)
	private Event event;

	public LocationSeatHold() {
	}

	public LocationSeatHoldPK getId() {
		return this.id;
	}

	public void setId(LocationSeatHoldPK id) {
		this.id = id;
	}

	public Timestamp getExpirationTimestamp() {
		return this.expirationTimestamp;
	}

	public void setExpirationTimestamp(Timestamp expirationTimestamp) {
		this.expirationTimestamp = expirationTimestamp;
	}

	public Integer getRowVersion() {
		return this.rowVersion;
	}

	public void setRowVersion(Integer rowVersion) {
		this.rowVersion = rowVersion;
	}

	public AvailableSeat getAvailableSeat() {
		return this.availableSeat;
	}

	public void setAvailableSeat(AvailableSeat availableSeat) {
		this.availableSeat = availableSeat;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<LocationSeatReservation> getLocationSeatReservations() {
		return this.locationSeatReservations;
	}

	public void setLocationSeatReservations(List<LocationSeatReservation> locationSeatReservations) {
		this.locationSeatReservations = locationSeatReservations;
	}

	public LocationSeatReservation addLocationSeatReservations(LocationSeatReservation locationSeatReservations) {
		getLocationSeatReservations().add(locationSeatReservations);
		locationSeatReservations.setLocationSeatHold(this);

		return locationSeatReservations;
	}

	public LocationSeatReservation removeLocationSeatReservations1(LocationSeatReservation locationSeatReservations) {
		getLocationSeatReservations().remove(locationSeatReservations);
		locationSeatReservations.setLocationSeatHold(null);

		return locationSeatReservations;
	}

	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}