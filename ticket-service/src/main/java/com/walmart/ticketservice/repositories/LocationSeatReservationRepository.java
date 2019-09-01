package com.walmart.ticketservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.walmart.ticketservice.entities.LocationSeatHoldPK;
import com.walmart.ticketservice.entities.LocationSeatReservation;

/**
 * @author Aremaniche This is repository class for LocationSeatReservation
 *         entity .
 */
@Repository
public interface LocationSeatReservationRepository extends CrudRepository<LocationSeatReservation, LocationSeatHoldPK> {

	/**
	 * This Method returns list of reservations for hold id .
	 * 
	 * @param holdId
	 * @return
	 */
	@Query("select  locSeatHoldRes  from LocationSeatReservation locSeatHoldRes    where locSeatHoldRes.locationSeatHold.id.holdId=:holdId ")
	public List<LocationSeatReservation>  findReservationsForHoldId(@Param("holdId") Long holdId);

}
