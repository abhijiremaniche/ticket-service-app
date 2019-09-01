package com.walmart.ticketservice.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author Aremaniche
 * The persistent class for the LOCATION_SEAT_RESERVATION database table.
 * 
 */
@Entity
@Table(name = "LOCATION_SEAT_RESERVATION")
@NamedQuery(name = "LocationSeatReservation.findAll", query = "SELECT l FROM LocationSeatReservation l")
public class LocationSeatReservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "LOCATION_SEAT_RESERVATION_RESERVATIONID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCATION_SEAT_RESERVATION_RESERVATIONID_GENERATOR")
	@Column(name = "RESERVATION_ID")
	private Long reservationId;
	
	@Version
	@Column(name = "ROW_VERSION")
	private Integer rowVersion;

	@Column(name = "CONFIRM_CODE")
	private String confirmationCode;

	// bi-directional many-to-one association to LocationSeatHold
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "EVENT_ID", referencedColumnName = "EVENT_ID"),
			@JoinColumn(name = "HOLD_ID", referencedColumnName = "HOLD_ID"),
			@JoinColumn(name = "LOCATION_ID", referencedColumnName = "LOCATION_ID"),
			@JoinColumn(name = "SEAT_ID", referencedColumnName = "SEAT_ID"),
			@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID") })
	private LocationSeatHold locationSeatHold;

	public LocationSeatReservation() {
	}

	public Long getReservationId() {
		return this.reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public Integer getRowVersion() {
		return this.rowVersion;
	}

	public void setRowVersion(Integer rowVersion) {
		this.rowVersion = rowVersion;
	}

	public LocationSeatHold getLocationSeatHold() {
		return this.locationSeatHold;
	}

	public void setLocationSeatHold(LocationSeatHold locationSeatHold) {
		this.locationSeatHold = locationSeatHold;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

}