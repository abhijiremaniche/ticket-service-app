package com.walmart.ticketservice.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.walmart.ticketservice.dto.EventDetails;
import com.walmart.ticketservice.dto.LocationDetails;
import com.walmart.ticketservice.dto.ReservationResponse;
import com.walmart.ticketservice.dto.Seat;
import com.walmart.ticketservice.dto.SeatHold;
import com.walmart.ticketservice.entities.AvailableSeat;
import com.walmart.ticketservice.entities.Event;
import com.walmart.ticketservice.entities.Location;
import com.walmart.ticketservice.entities.LocationSeatHold;
import com.walmart.ticketservice.entities.LocationSeatHoldPK;
import com.walmart.ticketservice.entities.LocationSeatReservation;
import com.walmart.ticketservice.entities.User;
import com.walmart.ticketservice.exeptions.ExpiredHoldIdRequestedException;
import com.walmart.ticketservice.exeptions.HoldIdAlreadyUsedForReservationException;
import com.walmart.ticketservice.exeptions.NotEnoughSeatsAvailableToHoldException;
import com.walmart.ticketservice.repositories.AvailableSeatRepository;
import com.walmart.ticketservice.repositories.EventRepository;
import com.walmart.ticketservice.repositories.LocationRepository;
import com.walmart.ticketservice.repositories.LocationSeatHoldRepository;
import com.walmart.ticketservice.repositories.LocationSeatReservationRepository;
import com.walmart.ticketservice.repositories.UserRepository;
import com.walmart.ticketservice.utils.DateTimeService;
/**
 * @author Aremaniche 
 *This is reservation service implementation which provide following function.
 *First- The number of seats in the venue that are neither held nor reserved.
 *Second-Find and hold the best available seats for a customer
 *Third-Commit seats held for a specific customer  */

@Service
public class ReservationServiceImpl implements ReservationService {

	private static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

	@Autowired
	private AvailableSeatRepository availableSeatRepository;

	@Autowired
	private LocationSeatHoldRepository locationSeatHoldRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private LocationSeatReservationRepository locationSeatReservationRepository;

	@Autowired
	private DateTimeService dateTimeService;
	
	@Value("${seatholdtimeout}")	 
	private int NUMBER_OF_SECONDS ;

	 
	/* (non-Javadoc)
	 * @see com.walmart.ticketservice.services.ReservationService#numSeatsAvailable(java.lang.Long, java.lang.Long)
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public int numSeatsAvailable(Long locationId, Long eventId) {

		logger.debug("Number of seats available requested for locationID {} and eventId{}", locationId, eventId);

		Integer numSeatsAvailable = availableSeatRepository
				.findAvailabeSeatByLocationIdAndEventId(locationId, eventId, dateTimeService.getBusinessDate()).size();
		logger.debug("There are total {} available seats", numSeatsAvailable);
		return numSeatsAvailable;
	}

 
	/* (non-Javadoc)
	 * @see com.walmart.ticketservice.services.ReservationService#findAndHoldSeats(java.lang.Long, java.lang.Long, int, java.lang.String)
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	@Override
	public SeatHold findAndHoldSeats(Long locationId, Long eventId, int numSeats, String customerEmail) {

		logger.debug("Customer {}  is requesting {} seats for hold  for locationID {} and eventId{}", customerEmail,
				eventId, locationId, eventId);

		User customer = userRepository.findByusername(customerEmail);
		
		List<LocationSeatHold> expiredLocationSeatHolds = locationSeatHoldRepository
				.findExpiredLocationSeatHoldByLocationIdAndEventId(locationId, eventId,
						dateTimeService.getBusinessDate());

		expiredLocationSeatHolds.stream()
				.forEach((expiredLocationSeatHold) -> locationSeatHoldRepository.delete(expiredLocationSeatHold));

		List<AvailableSeat> availableSeatsForHolds = availableSeatRepository
				.findAvailabeSeatByLocationIdAndEventId(locationId, eventId, dateTimeService.getBusinessDate());

		logger.debug("Available seat holds {}  for location: {} and event: {}", availableSeatsForHolds.size(),
				locationId, eventId);
		if (numSeats <= availableSeatsForHolds.size()) {
			logger.debug("Available seat holds {}  are greater than or equal to requested seat holds {}",
					availableSeatsForHolds.size(), numSeats);

			SeatHold seatHold = new SeatHold();
			seatHold.setUsername(customerEmail);
			Optional<Location> locationOpt = locationRepository.findById(locationId);
			if (locationOpt.isPresent()) {
				LocationDetails locationDetails = new LocationDetails();
				locationDetails.setAddress(locationOpt.get().getLocationAddress());
				locationDetails.setLocationName(locationOpt.get().getLocationNm());
				seatHold.setLocationDetails(locationDetails);
			}

			Optional<Event> eventOpt = eventRepository.findById(eventId);
			if (eventOpt.isPresent()) {
				EventDetails eventDetails = new EventDetails();
				eventDetails.setEventName(eventOpt.get().getEventNm());
				seatHold.setEventDetails(eventDetails);

			}
			Timestamp expirationTimeStamp = new Timestamp(
					dateTimeService.addSeconds(dateTimeService.getBusinessDate(), NUMBER_OF_SECONDS).getTime());
			Long seatHoldId = locationSeatHoldRepository.getNextSeatHoldId();
			seatHold.setSeatHoldId(seatHoldId);
			logger.debug("Generated Hold id {} for customer {}", seatHoldId, customerEmail);

			logger.debug("Generated Hold id {} will expire at {}", seatHoldId, expirationTimeStamp);

			availableSeatsForHolds.stream().limit(numSeats).forEach((availableSeat) -> {
				LocationSeatHold locationSeatHold = new LocationSeatHold();
				LocationSeatHoldPK locationSeatHoldPK = new LocationSeatHoldPK();
				locationSeatHoldPK.setLocationId(locationId);
				locationSeatHoldPK.setEventId(eventId);
				locationSeatHoldPK.setSeatId(availableSeat.getSeatId());
				locationSeatHoldPK.setUserId(customer.getUserId());
				locationSeatHoldPK.setHoldId(seatHoldId);
				locationSeatHold.setId(locationSeatHoldPK);
				locationSeatHold.setExpirationTimestamp(expirationTimeStamp);
				locationSeatHold = locationSeatHoldRepository.save(locationSeatHold);

				Seat seat = new Seat();
				seat.setSeatLocation(availableSeat.getSeatLocation());
				seatHold.getSeats().add(seat);

			});

			logger.debug("Customer {} has seat hold for seats {}  for location: {} and event: {} ", customerEmail,
					seatHold.getSeats(), seatHold.getLocationDetails().getLocationName(),
					seatHold.getEventDetails().getEventName());
			return seatHold;
		} else {
			logger.debug("Available seat holds {}  are less than requested seat holds {}",
					availableSeatsForHolds.size(), numSeats);
			throw new NotEnoughSeatsAvailableToHoldException("Seats are not avilable to hold");

		}

	}

 
	/* (non-Javadoc)
	 * @see com.walmart.ticketservice.services.ReservationService#reserveSeats(java.lang.Long, java.lang.String)
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	@Override
	public ReservationResponse reserveSeats(Long seatHoldId, String customerEmail) {

		logger.debug("Customer {}  is requesting reservation for seat hold id {}", customerEmail, seatHoldId);

		User customer = userRepository.findByusername(customerEmail);
		
		if(!locationSeatReservationRepository.findReservationsForHoldId(seatHoldId).isEmpty()) {
			throw new HoldIdAlreadyUsedForReservationException("This hold id is already used for reservation.");
		 }
	 
		List<LocationSeatHold> locationSeatHolds = locationSeatHoldRepository.findBySeatHoldsBySeatHoldIdAndUserId(
				seatHoldId, customer.getUserId(), dateTimeService.getBusinessDate());

		UUID uuid = UUID.randomUUID();
		String confirmationCode = uuid.toString().toUpperCase();
		logger.debug("Generated Confirmation code {} for customer {} ", confirmationCode, customerEmail);

		if (!locationSeatHolds.isEmpty()) {
			logger.debug("Location seat holds are available to reserve.");

			ReservationResponse reservationResponse = new ReservationResponse();
			reservationResponse.setUsername(customerEmail);
			Location location = locationSeatHolds.get(0).getLocation();
			LocationDetails locationDetails = new LocationDetails();
			locationDetails.setAddress(location.getLocationAddress());
			locationDetails.setLocationName(location.getLocationNm());
			reservationResponse.setLocationDetails(locationDetails);

			EventDetails eventDetails = new EventDetails();
			eventDetails.setEventName(locationSeatHolds.get(0).getEvent().getEventNm());
			reservationResponse.setEventDetails(eventDetails);

			reservationResponse.setConfirmationCode(confirmationCode);

			locationSeatHolds.stream().forEach((locationSeatHold) -> {

				LocationSeatReservation locationSeatReservation = new LocationSeatReservation();
				locationSeatReservation.setLocationSeatHold(locationSeatHold);
				locationSeatReservation.setConfirmationCode(confirmationCode);

				Seat seat = new Seat();
				seat.setSeatLocation(locationSeatHold.getAvailableSeat().getSeatLocation());
				reservationResponse.getSeats().add(seat);
				locationSeatReservationRepository.save(locationSeatReservation);

			});

			logger.debug("Customer {} has successfully  reserved for seats {}  for location {} and event{} ",
					customerEmail, reservationResponse.getSeats(),
					reservationResponse.getLocationDetails().getLocationName(),
					reservationResponse.getEventDetails().getEventName());
			return reservationResponse;
		} else {
			logger.debug("Expired hold id was requested for reservation.");

			throw new ExpiredHoldIdRequestedException("Expired hold id was requested for reservation.");
		}

	}
}
