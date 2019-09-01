package com.walmart.ticketservice.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * @author Aremaniche
 * The persistent class for the USER database table.
 * 
 */
@Entity
@Table(name = "USER_DETAIL", catalog = "", schema = "")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USER_USERID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_USERID_GENERATOR")
	@Column(name="USER_ID")
	private Long userId;

	@Column(name="ROW_VERSION")
	private Integer rowVersion;
	
	@Column(name="USERNAME")
	private String username;

	//bi-directional many-to-one association to LocationSeatHold
	@OneToMany(mappedBy="user")
	private List<LocationSeatHold> locationSeatHolds;

	public User() {
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getRowVersion() {
		return this.rowVersion;
	}

	public void setRowVersion(Integer rowVersion) {
		this.rowVersion = rowVersion;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<LocationSeatHold> getLocationSeatHolds() {
		return this.locationSeatHolds;
	}

	public void setLocationSeatHolds(List<LocationSeatHold> locationSeatHolds) {
		this.locationSeatHolds = locationSeatHolds;
	}

	public LocationSeatHold addLocationSeatHold(LocationSeatHold locationSeatHold) {
		getLocationSeatHolds().add(locationSeatHold);
		locationSeatHold.setUser(this);

		return locationSeatHold;
	}

	public LocationSeatHold removeLocationSeatHold(LocationSeatHold locationSeatHold) {
		getLocationSeatHolds().remove(locationSeatHold);
		locationSeatHold.setUser(null);

		return locationSeatHold;
	}

}