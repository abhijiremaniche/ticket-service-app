package com.walmart.ticketservice.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.walmart.ticketservice.entities.AvailableSeat;
/**
 * @author Aremaniche
 *This is repository class for AvailableSeat entity .
 */
@Repository
public interface AvailableSeatRepository extends CrudRepository<AvailableSeat, Long> {

	/**
	 * This method returns available seats in the venue that are neither held nor reserved.
	 * It also considers expiration date and returns expired seat. 
	 * @param locationId
	 * @param eventId
	 * @param currentDate
	 * @return
	 */
	@Query("SELECT availseats FROM AvailableSeat availseats WHERE  "
			+ " NOT EXISTS ( SELECT 1 FROM LocationSeatHold lsh WHERE lsh.id.seatId= availseats.seatId and  lsh.expirationTimestamp >=:currentDate  and lsh.id.locationId=:locationId and   lsh.id.eventId=:eventId)"
			+ " AND "
			+ " NOT EXISTS ( SELECT 1 FROM LocationSeatReservation lsr WHERE lsr.locationSeatHold.availableSeat.seatId= availseats.seatId and  lsr.locationSeatHold.location.locationId=:locationId and  lsr.locationSeatHold.event.eventId=:eventId  ) "
			+ " AND availseats.location.locationId=:locationId "
			+ " AND EXISTS ( SELECT 1 FROM Event e where location.locationId=availseats.location.locationId and e.eventId=:eventId   ) ")
	
	public List<AvailableSeat> findAvailabeSeatByLocationIdAndEventId(@Param("locationId") Long locationId, @Param("eventId") Long eventId,
			@Param("currentDate") Date currentDate);

}
