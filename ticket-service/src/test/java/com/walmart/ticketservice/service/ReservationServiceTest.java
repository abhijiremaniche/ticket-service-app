package com.walmart.ticketservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.walmart.ticketservice.dto.ReservationResponse;
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
import com.walmart.ticketservice.services.ReservationService;
import com.walmart.ticketservice.utils.DateTimeService;

/**
 * @author Aremaniche
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {

	@Autowired
	private ReservationService reservationService;

	@MockBean
	private AvailableSeatRepository availableSeatRepository;

	@MockBean
	private LocationSeatHoldRepository locationSeatHoldRepository;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private LocationRepository locationRepository;

	@MockBean
	private EventRepository eventRepository;

	@MockBean
	private LocationSeatReservationRepository locationSeatReservationRepository;

	private List<AvailableSeat> availableSeats;

	private User user;

	private List<LocationSeatHold> locationSeatHolds;
	
	private List<LocationSeatReservation> reservedlocationSeatHolds;

	private Location location;

	private Event event;

	@Autowired
	private DateTimeService dateTimeService;

	/**
	 * This method  setups test data for test case.
	 */
	@Before
	public void setUp() {

		this.availableSeats = new ArrayList<>();
		AvailableSeat availableSeat = new AvailableSeat();
		availableSeat.setSeatLocation("A5");
		location = new Location();
		location.setLocationId(100l);
		location.setLocationNm("SALEM COMMUNITY HALL");
		location.setLocationAddress("SALEM,OREGON");
		availableSeat.setLocation(location);
		availableSeats.add(availableSeat);

		this.user = new User();
		this.user.setUserId(1l);
		this.user.setUsername("username@username.com");

		this.event = new Event();

		this.event.setEventNm("SCHOOL FUNCTION");

		this.locationSeatHolds = new ArrayList<>();
		LocationSeatHold locationSeatHold = new LocationSeatHold();
		LocationSeatHoldPK id = new LocationSeatHoldPK();
		id.setEventId(1l);
		id.setHoldId(9000l);
		id.setSeatId(200l);
		id.setUserId(200l);
		locationSeatHold.setId(id);
		locationSeatHold.setLocation(location);
		locationSeatHold.setEvent(event);
		locationSeatHold.setAvailableSeat(availableSeat);

		Timestamp expirationTimeStamp = new Timestamp(dateTimeService.getBusinessDate().getTime());
		locationSeatHold.setExpirationTimestamp(expirationTimeStamp);
		locationSeatHolds.add(locationSeatHold);
		
		LocationSeatReservation locationSeatReservation =new LocationSeatReservation();
		locationSeatReservation.setLocationSeatHold(locationSeatHold);
		UUID uuid = UUID.randomUUID();
		String confirmationCode = uuid.toString().toUpperCase();
		locationSeatReservation.setConfirmationCode(confirmationCode);
		reservedlocationSeatHolds=new ArrayList<>();
		reservedlocationSeatHolds.add(locationSeatReservation);
		

	}

	
	/**
	 * This tests numSeatsAvailable method of Reservation
	 * Service and makes sure that service is returning same number
	 * of seats sa return by availableSeatRepository.findAvailabeSeatByLocationIdAndEventId
	 */
	@Test
	public void numSeatsAvailableTest() {

		Mockito.when(availableSeatRepository.findAvailabeSeatByLocationIdAndEventId(Mockito.anyLong(),
				Mockito.anyLong(), Mockito.any(Date.class))).thenReturn(this.availableSeats);
		Integer numSeatsAvailable = reservationService.numSeatsAvailable(1l, 1l);
		assertEquals(numSeatsAvailable, new Integer(1));

	}

	/**
	 * This method test findAndHoldSeats method of reservation service
	 * 
	 */
	@Test
	public void findAndHoldSeatsTest() {

		Mockito.when(userRepository.findByusername(Mockito.anyString())).thenReturn(this.user);

		Mockito.when(locationSeatHoldRepository.findExpiredLocationSeatHoldByLocationIdAndEventId(Mockito.anyLong(),
				Mockito.anyLong(), Mockito.any(Date.class))).thenReturn(this.locationSeatHolds);

		Mockito.when(availableSeatRepository.findAvailabeSeatByLocationIdAndEventId(Mockito.anyLong(),
				Mockito.anyLong(), Mockito.any(Date.class))).thenReturn(this.availableSeats);

		Mockito.when(locationRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(location));

		Mockito.when(eventRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(event));

		Mockito.when(locationSeatHoldRepository.getNextSeatHoldId()).thenReturn(100l);

		SeatHold seatHold = reservationService.findAndHoldSeats(1l, 1l, 1, "username@username.com");

		assertEquals(this.user.getUsername(), seatHold.getUsername());
		assertEquals(Long.valueOf("100"), seatHold.getSeatHoldId());
		assertEquals(this.locationSeatHolds.size(), seatHold.getSeats().size());
		assertEquals(this.location.getLocationNm(), seatHold.getLocationDetails().getLocationName());
		assertEquals(this.location.getLocationAddress(), seatHold.getLocationDetails().getAddress());
		assertEquals(this.event.getEventNm(), seatHold.getEventDetails().getEventName());

	}

	/**
	 * This method test findAndHoldSeats method of reservation service.
	 * It validates that service throws NotEnoughSeatsAvailableToHoldException when enough
	 * seats are not available
	 */
	@Test(expected = NotEnoughSeatsAvailableToHoldException.class)
	public void findAndHoldSeatsNotEnoughSeatsAvailableToHoldExceptionTest() {

		Mockito.when(userRepository.findByusername(Mockito.anyString())).thenReturn(this.user);

		Mockito.when(locationSeatHoldRepository.findExpiredLocationSeatHoldByLocationIdAndEventId(Mockito.anyLong(),
				Mockito.anyLong(), Mockito.any(Date.class))).thenReturn(this.locationSeatHolds);

		Mockito.when(availableSeatRepository.findAvailabeSeatByLocationIdAndEventId(Mockito.anyLong(),
				Mockito.anyLong(), Mockito.any(Date.class))).thenReturn(this.availableSeats);

		Mockito.when(locationRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(location));

		Mockito.when(eventRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(event));

		Mockito.when(locationSeatHoldRepository.getNextSeatHoldId()).thenReturn(100l);

		reservationService.findAndHoldSeats(1l, 1l, 2, "username@username.com");

	}

	/**
	 * This method test reserveSeats method of reservation service.
	 * 
	 */
	@Test
	public void reserveSeatsTest() {

		Mockito.when(userRepository.findByusername(Mockito.anyString())).thenReturn(this.user);

		Mockito.when(locationSeatHoldRepository.findBySeatHoldsBySeatHoldIdAndUserId(Mockito.anyLong(),
				Mockito.anyLong(), Mockito.any(Date.class))).thenReturn(this.locationSeatHolds);

		ReservationResponse reservationResponse = reservationService.reserveSeats(100l, "username@username.com");
		assertNotNull(reservationResponse.getConfirmationCode());
		assertEquals(this.event.getEventNm(), reservationResponse.getEventDetails().getEventName());
		assertEquals(this.location.getLocationNm(), reservationResponse.getLocationDetails().getLocationName());
		assertEquals(this.location.getLocationAddress(), reservationResponse.getLocationDetails().getAddress());
		assertEquals(this.locationSeatHolds.size(), reservationResponse.getSeats().size());
		assertEquals(this.user.getUsername(), reservationResponse.getUsername());

	}

	/**
	 * This method test reserveSeats method of reservation service.
	 * It validates that service throws ExpiredHoldIdRequestedException when 
	 * reservation is requested on invalid hold id
	 */
	@Test(expected = ExpiredHoldIdRequestedException.class)
	public void reserveSeatsExpiredHoldIdRequestedExceptionTest() {

		Mockito.when(userRepository.findByusername(Mockito.anyString())).thenReturn(this.user);

		Mockito.when(locationSeatHoldRepository.findBySeatHoldsBySeatHoldIdAndUserId(Mockito.anyLong(),
				Mockito.anyLong(), Mockito.any(Date.class))).thenReturn(new ArrayList<>());

		reservationService.reserveSeats(100l, "username@username.com");

	}

	/**
	 * This method test findAndHoldSeats method of reservation service.
	 * It validates that service throws HoldIdAlreadyUsedForReservationException when 
	 * reservation is requested on existing hold id
	 */
	@Test(expected = HoldIdAlreadyUsedForReservationException.class)
	public void reserveSeatsHoldIdAlreadyUsedForReservationExceptionTest() {

		Mockito.when(userRepository.findByusername(Mockito.anyString())).thenReturn(this.user);

		Mockito.when(locationSeatHoldRepository.findBySeatHoldsBySeatHoldIdAndUserId(Mockito.anyLong(),
				Mockito.anyLong(), Mockito.any(Date.class))).thenReturn(new ArrayList<>());

		Mockito.when(locationSeatReservationRepository.findReservationsForHoldId(Mockito.anyLong()))
				.thenReturn(reservedlocationSeatHolds);
		reservationService.reserveSeats(100l, "username@username.com");

	}

}
