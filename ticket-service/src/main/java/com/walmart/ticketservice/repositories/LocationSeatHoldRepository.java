package com.walmart.ticketservice.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.walmart.ticketservice.entities.LocationSeatHold;
import com.walmart.ticketservice.entities.LocationSeatHoldPK;

/**
 * @author Aremaniche This is repository class for LocationSeatHold entity .
 */
@Repository
public interface LocationSeatHoldRepository extends CrudRepository<LocationSeatHold, LocationSeatHoldPK> {

	/**
	 * This method find out all expired seat hold for specific location and Event.
	 * 
	 * @param locId
	 * @param eventId
	 * @param currentDate
	 * @return
	 */
	@Query("select  locSeatHold  from LocationSeatHold locSeatHold where locSeatHold.id.eventId=:eventId "
			+ " and locSeatHold.id.locationId=:locId and locSeatHold.expirationTimestamp<:currentDate "
			+ " and NOT EXISTS ( SELECT 1 FROM LocationSeatReservation lsr WHERE lsr.locationSeatHold.id.holdId =locSeatHold.id.holdId  and lsr.locationSeatHold.id.locationId =locSeatHold.id.locationId  and   lsr.locationSeatHold.id.seatId=locSeatHold.id.seatId and   lsr.locationSeatHold.id.eventId=locSeatHold.id.eventId      )"

	)
	public List<LocationSeatHold> findExpiredLocationSeatHoldByLocationIdAndEventId(@Param("locId") Long locId,
			@Param("eventId") Long eventId, @Param("currentDate") Date currentDate);

	/**
	 * This Method find all Location Seat Holds by hold Id
	 * 
	 * @param holdId
	 * @return
	 */
	@Query("select  locSeatHold  from LocationSeatHold locSeatHold where locSeatHold.id.holdId=:holdId ")
	public List<LocationSeatHold> findLocationSeatHoldByHoldId(@Param("holdId") Long holdId);

	/**
	 * This method generates next value for hold id
	 * 
	 * @return
	 */
	@Query(value = "VALUES (NEXT VALUE FOR SEAT_HOLD_ID_GENERATOR)", nativeQuery = true)

	public Long getNextSeatHoldId();

	/**
	 * This method finds seatHolds by hold id and user id
	 * 
	 * @param holdId
	 * @param userId
	 * @param currentDate
	 * @return
	 */
	@Query("select locSeatHold  from  LocationSeatHold  locSeatHold where locSeatHold.id.holdId=:holdId and locSeatHold.id.userId=:userId and  locSeatHold.expirationTimestamp>=:currentDate")
	public List<LocationSeatHold> findBySeatHoldsBySeatHoldIdAndUserId(@Param("holdId") Long holdId,
			@Param("userId") Long userId, @Param("currentDate") Date currentDate);

}
