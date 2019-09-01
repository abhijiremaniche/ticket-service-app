package com.walmart.ticketservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.walmart.ticketservice.dto.ReservationRequest;
import com.walmart.ticketservice.dto.ReservationResponse;
import com.walmart.ticketservice.dto.SeatHold;
import com.walmart.ticketservice.dto.SeatHoldRequest;
import com.walmart.ticketservice.services.ReservationService;
import com.walmart.ticketservice.services.ValidatorService;

/**
 * @author Aremaniche
 *This is rest controller.It provide mainly three different services.
 *First- The number of seats in the venue that are neither held nor reserved.
 *Second-Find and hold the best available seats for a customer
 *Third-Commit seats held for a specific customer
*/

@RestController
public class ShowReservationController {

	private static final Logger logger = LoggerFactory.getLogger(ShowReservationController.class);

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private ValidatorService validatorService;

	/**
	 * This service will returns number of seats available for requested location
	 * and event id.It also validates if requested location id and event id is valid
	 * or not.If location is invalid it throws InvalidLocationException and If event
	 * is Invalid throws InvalidEventException
	 * 
	 * @param locationId This represents location id
	 * @param eventId    This represents event id
	 * @return number of seats available
	 */
	@GetMapping(path = "/numSeatsAvailable/{locationId}/{eventId}")
	public Integer numSeatsAvailable(@PathVariable("locationId") Long locationId,
			@PathVariable("eventId") Long eventId) {
		logger.debug("Number of seats available requested for locationID {} and eventId{}", locationId, eventId);

		validatorService.validateNumSeatsAvailableRequest(locationId, eventId);
		Integer numSeatsAvailable = reservationService.numSeatsAvailable(locationId, eventId);
		logger.debug("There are total {} available seats", numSeatsAvailable);
		return numSeatsAvailable;
	}

	/**
	 * This service will obtains seat holds for a particular location and event
	 * behalf of customer. Once hold is complete it return seat location/number. It
	 * also validates if requested customer ,location id and event id is valid or
	 * not.If location is invalid it throws InvalidLocationException and If event is
	 * Invalid throws InvalidEventException.If customer is not valid it throws
	 * InvalidUserException Exception
	 * 
	 * @param seatHoldRequest This object hold requested number of seats
	 * @return Object that contains seat hold information
	 */
	@RequestMapping(value = "/findAndHoldSeats", method = RequestMethod.POST)
	public SeatHold findAndHoldSeats(@RequestBody SeatHoldRequest seatHoldRequest) {
		logger.debug("Customer {}  is requesting {} seats for hold  for locationID {} and eventId{}",
				seatHoldRequest.getUserName(), seatHoldRequest.getNumberOfSeats(), seatHoldRequest.getLocationId(),
				seatHoldRequest.getEvenlId());

		validatorService.validateFindAndHoldSeatsRequest(seatHoldRequest.getUserName(), seatHoldRequest.getLocationId(),
				seatHoldRequest.getEvenlId());

		SeatHold seatHold = reservationService.findAndHoldSeats(seatHoldRequest.getLocationId(),
				seatHoldRequest.getEvenlId(), seatHoldRequest.getNumberOfSeats(), seatHoldRequest.getUserName());

		logger.debug("Customer {} has seat hold for seats {}  for location: {} and event: {} ",
				seatHoldRequest.getUserName(), seatHold.getSeats(), seatHold.getLocationDetails().getLocationName(),
				seatHold.getEventDetails().getEventName());

		return seatHold;
	}

	/**
	 * This service will reserve seats for requested hold id and customer.Once
	 * Reservation is complete it return seat location/number. It also validates if
	 * requested hold id & customer is valid or not. If Hold ID is invalid it throws
	 * InvalidHoldIDException Exception if customer is valid or not it throws
	 * InvalidUserException Exception
	 * 
	 * @param reservationRequest
	 * @return Object that contain reservation information
	 */
	@RequestMapping(value = "/reserveSeats", method = RequestMethod.POST)
	public ReservationResponse reserveSeats(@RequestBody ReservationRequest reservationRequest) {

		logger.debug("Customer {}  is requesting reservation for seat hold id {}", reservationRequest.getUserName(),
				reservationRequest.getSeatHoldId());
		validatorService.validateReserveSeatsRequest(reservationRequest.getUserName(),
				reservationRequest.getSeatHoldId());

		ReservationResponse reservationResponse = reservationService.reserveSeats(reservationRequest.getSeatHoldId(),
				reservationRequest.getUserName());

		logger.debug("Customer {} has successfully  reserved for seats {}  for location {} and event{} ",
				reservationRequest.getUserName(), reservationResponse.getSeats(),
				reservationResponse.getLocationDetails().getLocationName(),
				reservationResponse.getEventDetails().getEventName());

		return reservationResponse;
	}

}
