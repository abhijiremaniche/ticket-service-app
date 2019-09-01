package com.walmart.ticketservice.services;
/**
 * @author Aremaniche
 *This is validator service interface
 *It provide methods to validate input parameters
 */
public interface ValidatorService {
	/**
	 * This method validates event id.
	 * @param eventId
	 */
	public void validateEvent(Long eventId);

	/**
	 * This method validates Hold id.
	 * @param holdId
	 */
	public void validateHoldId(Long holdId);

	/**
	 * This method validates location  id.
	 * @param locationId
	 */
	public void validateLocation(Long locationId);

	/**
	 * This method validates user/customer
	 * @param customerEmail
	 */
	public void validateCustomer(String customerEmail);

	/**
	 * This method validates event id and location id
	 * @param locationId
	 * @param eventId
	 */
	public void validateNumSeatsAvailableRequest(Long locationId, Long eventId);

	/**
	 * This method validates user ,location and event id
	 * @param userName
	 * @param locationId
	 * @param eventId
	 */
	public void validateFindAndHoldSeatsRequest(String userName, Long locationId, long eventId);

	/**
	 * This method validate user and hold id
	 * @param userName
	 * @param holdId
	 */
	public void validateReserveSeatsRequest(String userName, Long holdId);
	
	 

}
