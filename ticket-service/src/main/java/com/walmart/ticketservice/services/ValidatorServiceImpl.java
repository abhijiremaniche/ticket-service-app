package com.walmart.ticketservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walmart.ticketservice.entities.User;
import com.walmart.ticketservice.exeptions.InvalidEventException;
import com.walmart.ticketservice.exeptions.InvalidHoldIDException;
import com.walmart.ticketservice.exeptions.InvalidLocationException;
import com.walmart.ticketservice.exeptions.InvalidUserException;
import com.walmart.ticketservice.repositories.EventRepository;
import com.walmart.ticketservice.repositories.LocationRepository;
import com.walmart.ticketservice.repositories.LocationSeatHoldRepository;
import com.walmart.ticketservice.repositories.UserRepository;

/**
 * @author Aremaniche This is validator service implementation It provide
 *         methods to validate input parameters
 */
@Service
public class ValidatorServiceImpl implements ValidatorService {

	private static final Logger logger = LoggerFactory.getLogger(ValidatorServiceImpl.class);
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private LocationSeatHoldRepository locationSeatHoldRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private UserRepository userRepository;

	 

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.walmart.ticketservice.services.ValidatorService#validateEvent(java.lang.
	 * Long)
	 */
	@Override
	public void validateEvent(Long eventId) {

		if (!eventRepository.findById(eventId).isPresent()) {
			logger.debug("Event Id {}  is not valid", eventId);
			throw new InvalidEventException("Event Id is not valid");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.walmart.ticketservice.services.ValidatorService#validateHoldId(java.lang.
	 * Long)
	 */
	@Override
	public void validateHoldId(Long holdId) {
		if (locationSeatHoldRepository.findLocationSeatHoldByHoldId(holdId).isEmpty()) {
			logger.debug("Hold Id {}  is not valid", holdId);
			throw new InvalidHoldIDException("Hold Id is not valid");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.walmart.ticketservice.services.ValidatorService#validateLocation(java.
	 * lang.Long)
	 */
	@Override
	public void validateLocation(Long locationId) {

		if (!locationRepository.findById(locationId).isPresent()) {
			logger.debug("Location Id  {}  is not valid", locationId);
			throw new InvalidLocationException("Location Id is not valid");

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.walmart.ticketservice.services.ValidatorService#validateCustomer(java.
	 * lang.String)
	 */
	public void validateCustomer(String customerEmail) {

		User customer = userRepository.findByusername(customerEmail);
		if (customer == null) {
			logger.debug("User Id {} is not valid", customerEmail);
			throw new InvalidUserException("User Id is not valid");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.walmart.ticketservice.services.ValidatorService#
	 * validateFindAndHoldSeatsRequest(java.lang.String, java.lang.Long, long)
	 */
	@Override
	public void validateFindAndHoldSeatsRequest(String userName, Long locationId, long eventId) {
		validateCustomer(userName);
		validateLocation(locationId);
		validateEvent(eventId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.walmart.ticketservice.services.ValidatorService#
	 * validateReserveSeatsRequest(java.lang.String, java.lang.Long)
	 */
	@Override
	public void validateReserveSeatsRequest(String userName, Long holdId) {

		validateCustomer(userName);
		validateHoldId(holdId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.walmart.ticketservice.services.ValidatorService#
	 * validateNumSeatsAvailableRequest(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void validateNumSeatsAvailableRequest(Long locationId, Long eventId) {
		validateLocation(locationId);
		validateEvent(eventId);

	}

	 

}
