package com.walmart.ticketservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.walmart.ticketservice.entities.Event;
import com.walmart.ticketservice.entities.Location;
import com.walmart.ticketservice.entities.LocationSeatHold;
import com.walmart.ticketservice.entities.User;
import com.walmart.ticketservice.exeptions.InvalidEventException;
import com.walmart.ticketservice.exeptions.InvalidHoldIDException;
import com.walmart.ticketservice.exeptions.InvalidLocationException;
import com.walmart.ticketservice.exeptions.InvalidUserException;
import com.walmart.ticketservice.repositories.EventRepository;
import com.walmart.ticketservice.repositories.LocationRepository;
import com.walmart.ticketservice.repositories.LocationSeatHoldRepository;
import com.walmart.ticketservice.repositories.UserRepository;
import com.walmart.ticketservice.services.ValidatorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidatorServiceTest {

	@Autowired
	private ValidatorService validatorService;
	@MockBean
	private EventRepository eventRepository;

	@MockBean
	private LocationSeatHoldRepository locationSeatHoldRepository;

	@MockBean
	private LocationRepository locationRepository;

	@MockBean
	private UserRepository userRepository;

	private Event event;
	private Location location;
	private User user;
	private List<LocationSeatHold> locationSeatHoldList;

	/**
	 * This sets up initial  data setup
	 */
	@Before
	public void setUp() {
		event = new Event();
		location = new Location();
		user = new User();
		locationSeatHoldList = new ArrayList<LocationSeatHold>();
		LocationSeatHold locationSeatHold = new LocationSeatHold();
		locationSeatHoldList.add(locationSeatHold);
	}

	/**
	 * This validates that validator Service service throws InvalidEventException exception when 
	 * Event id is invalid
	 */
	@Test(expected = InvalidEventException.class)
	public void validateEventTest() {
		Mockito.when(eventRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		validatorService.validateEvent(Mockito.anyLong());
	}

	/**
	 * This validates that validator Service service throws InvalidHoldIDException exception when 
	 * Hold id is invalid
	 */
	@Test(expected = InvalidHoldIDException.class)
	public void validateHoldIdTest() {

		Mockito.when(locationSeatHoldRepository.findLocationSeatHoldByHoldId(Mockito.anyLong()))
				.thenReturn(new ArrayList<LocationSeatHold>());
		validatorService.validateHoldId(Mockito.anyLong());
	}
	/**
	 * This validates that validator Service service throws InvalidHoldIDException exception when 
	 * Location  id is invalid
	 */
	@Test(expected = InvalidLocationException.class)
	public void validateLocationTest() {

		Mockito.when(locationRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		validatorService.validateLocation(Mockito.anyLong());

	}

	/**
	 * This validates that validator Service service throws InvalidHoldIDException exception when 
	 * User  id is invalid
	 */
	@Test(expected = InvalidUserException.class)
	public void validateCustomerTest() {

		Mockito.when(userRepository.findByusername(Mockito.anyString())).thenReturn(null);

		validatorService.validateCustomer(Mockito.anyString());

	}

	/**
	 * This validates that validator Service service throws InvalidLocationException exception when 
	 * Location   id is invalid
	 */
	@Test(expected = InvalidLocationException.class)
	public void validateNumSeatsAvailableRequestLocationIdTest() {

		Mockito.when(locationRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		Mockito.when(eventRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(event));

		validatorService.validateNumSeatsAvailableRequest(1l, 1l);

	}

	/**
	 * This validates that validator Service service throws InvalidLocationException exception when 
	 * Event  id is invalid
	 */
	@Test(expected = InvalidEventException.class)
	public void validateNumSeatsAvailableRequestEventIDTest() {

		Mockito.when(locationRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(location));
		Mockito.when(eventRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		validatorService.validateNumSeatsAvailableRequest(1l, 1l);

	}

	/**
	 * This validates that validator Service service throws InvalidUserException exception when 
	 * User  id is invalid
	 */
	@Test(expected = InvalidUserException.class)
	public void validateFindAndHoldSeatsRequestUserTest() {

		Mockito.when(userRepository.findByusername(Mockito.anyString())).thenReturn(null);
		Mockito.when(locationRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(location));
		Mockito.when(locationRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		validatorService.validateFindAndHoldSeatsRequest("username@username.com", 1l, 1l);
	}

	/**
	 * This validates that validator Service service throws InvalidLocationException exception when 
	 * location  id is invalid
	 */
	@Test(expected = InvalidLocationException.class)
	public void validateFindAndHoldSeatsRequestLocationIdTest() {

		Mockito.when(userRepository.findByusername(Mockito.anyString())).thenReturn(user);
		Mockito.when(locationRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		Mockito.when(locationRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		validatorService.validateFindAndHoldSeatsRequest("username@username.com", 1l, 1l);
	}
	/**
	 * This validates that validator Service service throws InvalidEventException exception when 
	 * Event  id is invalid
	 */
	@Test(expected = InvalidEventException.class)
	public void validateFindAndHoldSeatsRequestEventIDTest() {

		Mockito.when(userRepository.findByusername(Mockito.anyString())).thenReturn(user);
		Mockito.when(locationRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(location));
		Mockito.when(eventRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		validatorService.validateFindAndHoldSeatsRequest("username@username.com", 1l, 1l);
	}
	/**
	 * This validates that validator Service service throws InvalidUserException exception when 
	 * User  id is invalid
	 */
	@Test(expected = InvalidUserException.class)
	public void validateReserveSeatsRequestUserTest() {

		Mockito.when(userRepository.findByusername(Mockito.anyString())).thenReturn(null);
		Mockito.when(locationSeatHoldRepository.findLocationSeatHoldByHoldId(Mockito.anyLong()))
				.thenReturn(locationSeatHoldList);
		validatorService.validateReserveSeatsRequest("username@username.com", 1l);
	}
	/**
	 * This validates that validator Service service throws InvalidHoldIDException exception when 
	 * Hold  id is invalid
	 */
	@Test(expected = InvalidHoldIDException.class)
	public void validateReserveSeatsRequestHoldHoldIdTest( ) {
		Mockito.when(userRepository.findByusername(Mockito.anyString())).thenReturn(user);
		Mockito.when(locationSeatHoldRepository.findLocationSeatHoldByHoldId(Mockito.anyLong()))
				.thenReturn(new ArrayList<LocationSeatHold>());
		
		validatorService.validateReserveSeatsRequest("username@username.com", 1l);
	}
}
