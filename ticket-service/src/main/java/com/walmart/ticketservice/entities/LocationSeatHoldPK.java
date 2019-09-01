package com.walmart.ticketservice.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author Aremaniche
 * The primary key class for the LOCATION_SEAT_HOLD database table.
 * 
 */
@Embeddable
public class LocationSeatHoldPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="HOLD_ID")
	private Long holdId;

	@Column(name="LOCATION_ID" )
	private Long locationId;

	@Column(name="SEAT_ID" )
	private Long seatId;

	@Column(name="USER_ID" )
	private Long userId;

	@Column(name="EVENT_ID" )
	private Long eventId;

	public LocationSeatHoldPK() {
	}
	public Long getHoldId() {
		return this.holdId;
	}
	public void setHoldId(Long holdId) {
		this.holdId = holdId;
	}
	public Long getLocationId() {
		return this.locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public Long getSeatId() {
		return this.seatId;
	}
	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}
	public Long getUserId() {
		return this.userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public long getEventId() {
		return this.eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LocationSeatHoldPK)) {
			return false;
		}
		LocationSeatHoldPK castOther = (LocationSeatHoldPK)other;
		return 
			(this.holdId == castOther.holdId)
			&& (this.locationId == castOther.locationId)
			&& (this.seatId == castOther.seatId)
			&& (this.userId == castOther.userId)
			&& (this.eventId == castOther.eventId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.holdId ^ (this.holdId >>> 32)));
		hash = hash * prime + ((int) (this.locationId ^ (this.locationId >>> 32)));
		hash = hash * prime + ((int) (this.seatId ^ (this.seatId >>> 32)));
		hash = hash * prime + ((int) (this.userId ^ (this.userId >>> 32)));
		hash = hash * prime + ((int) (this.eventId ^ (this.eventId >>> 32)));
		
		return hash;
	}
}