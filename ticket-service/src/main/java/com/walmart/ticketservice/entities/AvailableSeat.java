package com.walmart.ticketservice.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * @author Aremaniche
 * The persistent class for the AVAILABLE_SEATS database table.
 * 
 */
@Entity
@Table(name = "AVAILABLE_SEATS")
@NamedQuery(name = "AvailableSeat.findAll", query = "SELECT a FROM AvailableSeat a")
public class AvailableSeat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "AVAILABLE_SEATS_SEATID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AVAILABLE_SEATS_SEATID_GENERATOR")
	@Column(name = "SEAT_ID")
	private Long seatId;

	@Column(name = "SEAT_LOCATION")
	private String seatLocation;
	@Version
	@Column(name = "VERSION_ID")
	private Integer versionId;

	// bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name = "LOCATION_ID")
	private Location location;

	// bi-directional many-to-one association to LocationSeatHold
	@OneToMany(mappedBy = "availableSeat")
	private List<LocationSeatHold> locationSeatHolds;

	public AvailableSeat() {
	}

	public Long getSeatId() {
		return this.seatId;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	public String getSeatLocation() {
		return this.seatLocation;
	}

	public void setSeatLocation(String seatLocation) {
		this.seatLocation = seatLocation;
	}

	public Integer getVersionId() {
		return this.versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<LocationSeatHold> getLocationSeatHolds() {
		return this.locationSeatHolds;
	}

	public void setLocationSeatHolds(List<LocationSeatHold> locationSeatHolds) {
		this.locationSeatHolds = locationSeatHolds;
	}

	public LocationSeatHold addLocationSeatHold(LocationSeatHold locationSeatHold) {
		getLocationSeatHolds().add(locationSeatHold);
		locationSeatHold.setAvailableSeat(this);

		return locationSeatHold;
	}

	public LocationSeatHold removeLocationSeatHold(LocationSeatHold locationSeatHold) {
		getLocationSeatHolds().remove(locationSeatHold);
		locationSeatHold.setAvailableSeat(null);

		return locationSeatHold;
	}

}