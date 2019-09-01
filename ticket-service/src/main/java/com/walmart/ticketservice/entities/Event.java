package com.walmart.ticketservice.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * @author Aremaniche
 * The persistent class for the EVENT database table.
 * 
 */
@Entity
@NamedQuery(name="Event.findAll", query="SELECT e FROM Event e")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="EVENT_EVENTID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EVENT_EVENTID_GENERATOR")
	@Column(name="EVENT_ID")
	private Long eventId;

	@Column(name="EVENT_NM")
	private String eventNm;
	@Version
	@Column(name="ROW_VERSION")
	private Integer rowVersion;

	//bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name="LOCATION_ID")
	private Location location;

	//bi-directional many-to-one association to LocationSeatHold
	@OneToMany(mappedBy="event")
	private List<LocationSeatHold> locationSeatHolds;

	public Event() {
	}

	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getEventNm() {
		return this.eventNm;
	}

	public void setEventNm(String eventNm) {
		this.eventNm = eventNm;
	}

	public Integer getRowVersion() {
		return this.rowVersion;
	}

	public void setRowVersion(Integer rowVersion) {
		this.rowVersion = rowVersion;
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
		locationSeatHold.setEvent(this);

		return locationSeatHold;
	}

	public LocationSeatHold removeLocationSeatHold(LocationSeatHold locationSeatHold) {
		getLocationSeatHolds().remove(locationSeatHold);
		locationSeatHold.setEvent(null);

		return locationSeatHold;
	}

}