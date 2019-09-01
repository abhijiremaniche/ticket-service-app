package com.walmart.ticketservice.services;

import com.walmart.ticketservice.dto.ReservationResponse;
import com.walmart.ticketservice.dto.SeatHold;

/**
 * @author Aremaniche 
 *This is reservation service interface which provide following function.
 *First- The number of seats in the venue that are neither held nor reserved.
 *Second-Find and hold the best available seats for a customer
 *Third-Commit seats held for a specific customer  */
public interface ReservationService {
	/**
	 * This method returns number of seats in the location( Venue ) that are neither
	 * held nor reserved for event id.It also validates if requested location id and
	 * event id is valid or not.
	 * 
	 * @param locationId
	 * @param eventId
	 * @return
	 */
	public int numSeatsAvailable(Long locationId, Long eventId);

	/**
	 * This method will obtains seat holds for a particular location and event
	 * behalf of customer. Once hold is complete it return seat location/numbers. If
	 * seats are not available then it throws
	 * NotEnoughSeatsAvailableToHoldException.It also delete expired seat holds for
	 * requested location id and event id.
	 * 
	 * @param locationId
	 * @param eventId
	 * @param numSeats
	 * @param customerEmail
	 * @return
	 */
	public SeatHold findAndHoldSeats(Long locationId, Long eventId, int numSeats, String customerEmail);

	/**
	 * This method will reserve seats for requested hold id and customer.Once
	 * Reservation is complete it return seat location/number.
	 * 
	 * @param seatHoldId
	 * @param customerEmail
	 * @return
	 */
	public ReservationResponse reserveSeats(Long seatHoldId, String customerEmail);
}
