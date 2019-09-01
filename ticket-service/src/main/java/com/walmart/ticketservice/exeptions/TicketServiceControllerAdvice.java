package com.walmart.ticketservice.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.walmart.ticketservice.dto.ErrorMessage;
/**
 * @author Aremaniche
 *This is Controller adviser class & used to handle business exceptions.
 */
@ControllerAdvice
public class TicketServiceControllerAdvice {
 
	/**
	 * This method handles InvalidEventException , InvalidHoldIDException , InvalidUserException ,InvalidLocationException and 
	 * send appropriate (HttpStatus.NOT_FOUND) status to client
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = { InvalidEventException.class, InvalidHoldIDException.class, InvalidUserException.class,
			InvalidLocationException.class })
	public ResponseEntity<ErrorMessage> handleInvalidInputExceptions(Exception exception) {
		return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
	}
	/**
	 * This method handles ExpiredHoldIdRequestedException , NotEnoughSeatsAvailableToHoldException and 
	 * send appropriate (HttpStatus.BAD_REQUEST) status to client
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = {ExpiredHoldIdRequestedException.class,NotEnoughSeatsAvailableToHoldException.class,HoldIdAlreadyUsedForReservationException.class})
	public ResponseEntity<ErrorMessage> handleSeatHoldsExceptions(Exception exception) {
		return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * This method handles any exceptions occurred in system.
	 * send appropriate (HttpStatus.BAD_REQUEST) status to client
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<ErrorMessage> handleExceptions(Exception exception) {
		return new ResponseEntity<>(new ErrorMessage("Server Internal Error!! Please try later"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
