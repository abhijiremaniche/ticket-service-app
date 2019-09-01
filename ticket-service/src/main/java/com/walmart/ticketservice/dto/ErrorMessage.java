package com.walmart.ticketservice.dto;

/**
 * @author Aremaniche This is Plain dto class .This is used to send error information to client application .
 */
public class ErrorMessage {
	private String errorMessage;

	public ErrorMessage(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
