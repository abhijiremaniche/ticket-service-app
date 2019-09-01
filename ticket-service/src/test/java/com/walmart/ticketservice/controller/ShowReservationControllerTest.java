package com.walmart.ticketservice.controller;

import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.walmart.ticketservice.controllers.ShowReservationController;
import com.walmart.ticketservice.dto.EventDetails;
import com.walmart.ticketservice.dto.LocationDetails;
import com.walmart.ticketservice.dto.ReservationResponse;
import com.walmart.ticketservice.dto.Seat;
import com.walmart.ticketservice.dto.SeatHold;
import com.walmart.ticketservice.exeptions.ExpiredHoldIdRequestedException;
import com.walmart.ticketservice.exeptions.HoldIdAlreadyUsedForReservationException;
import com.walmart.ticketservice.exeptions.InvalidEventException;
import com.walmart.ticketservice.exeptions.InvalidHoldIDException;
import com.walmart.ticketservice.exeptions.InvalidLocationException;
import com.walmart.ticketservice.exeptions.InvalidUserException;
import com.walmart.ticketservice.exeptions.NotEnoughSeatsAvailableToHoldException;
import com.walmart.ticketservice.services.ReservationService;
import com.walmart.ticketservice.services.ValidatorService;
/**
 * @author Aremaniche
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = ShowReservationController.class)
public class ShowReservationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	DataSource dataSource;

	@MockBean
	private ReservationService reservationService;

	@MockBean
	private ValidatorService validatorService;

	final String FIND_AND_HOLD_SEATS_REQUEST = "	{	    \"locationId\": 1,	    \"evenlId\": 1,	    \"numberOfSeats\": 2,	    \"userName\": \"username@username.com\"	    	    	}";
	final String FIND_AND_HOLD_SEATS_RESPONSE = " { \"seatHoldId\": 3000000057, \"username\": \"username@username.com\", \"seats\": [ {	  \"seatLocation\": \"A7\" }, { \"seatLocation\": \"A8\" } ], \"eventDetails\": {	  \"eventName\": \"SCHOOL FUNCTION\" }, \"locationDetails\": { \"locationName\":	 \"SALEM COMMUNITY HALL\", \"address\": \"SALEM,OREGON\" } }";

	final String RESERVE_SEATS_REQUEST = "{\"seatHoldId\": 3000000055,    \"userName\": \"username@username.com\"        }";
	final String RESERVE_SEATS_RESPONSE = "{ \"username\": \"username@username.com\",    \"seats\": [        {            \"seatLocation\": \"A5\"        },        {            \"seatLocation\": \"A6\"        }    ],    \"eventDetails\": {        \"eventName\": \"SCHOOL FUNCTION\"    },    \"locationDetails\": {        \"locationName\": \"SALEM COMMUNITY HALL\",        \"address\": \"SALEM,OREGON\"    },    \"confirmationCode\": \"EB6B06B8-E48B-4CD0-A8BD-845834F089EF\"}";
	private SeatHold seatHold;
	private ReservationResponse reservationResponse;

	@Before
	public void setUp() {
		seatHold = new SeatHold();
		seatHold.setSeatHoldId(3000000057l);
		seatHold.setUsername("username@username.com");
		Seat seata7 = new Seat();
		seata7.setSeatLocation("A7");
		seatHold.getSeats().add(seata7);
		Seat seata8 = new Seat();
		seata8.setSeatLocation("A8");
		seatHold.getSeats().add(seata8);
		EventDetails eventDetails = new EventDetails();
		eventDetails.setEventName("SCHOOL FUNCTION");
		seatHold.setEventDetails(eventDetails);
		LocationDetails locationDetails = new LocationDetails();
		locationDetails.setLocationName("SALEM COMMUNITY HALL");
		locationDetails.setAddress("SALEM,OREGON");
		seatHold.setLocationDetails(locationDetails);

		reservationResponse = new ReservationResponse();
		reservationResponse.setUsername("username@username.com");
		Seat seata5 = new Seat();
		seata5.setSeatLocation("A5");
		reservationResponse.getSeats().add(seata5);
		Seat seata6 = new Seat();
		seata6.setSeatLocation("A6");
		reservationResponse.getSeats().add(seata6);
		eventDetails = new EventDetails();
		eventDetails.setEventName("SCHOOL FUNCTION");
		reservationResponse.setEventDetails(eventDetails);
		locationDetails = new LocationDetails();
		locationDetails.setLocationName("SALEM COMMUNITY HALL");
		locationDetails.setAddress("SALEM,OREGON");
		reservationResponse.setLocationDetails(locationDetails);
		reservationResponse.setConfirmationCode("EB6B06B8-E48B-4CD0-A8BD-845834F089EF");

	}

	/**
	 * This is test case to validate /numSeatsAvailable end point
	 * It validates to make that service returning same number that is
	 * return by reservation service.
	 *  
	 * @throws Exception
	 */
	@Test
	public void testNumOfSeatsAvailable() throws Exception {
		Mockito.when(reservationService.numSeatsAvailable(Mockito.anyLong(), Mockito.anyLong())).thenReturn(10);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/numSeatsAvailable/1/1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
 		String expected = "10";
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	/**
	 * This is test case to validate /numSeatsAvailable end point when invalid 
	 * location id is sent as part of request.
	 * @throws Exception
	 */
	@Test
	public void testNumOfSeatsAvailableInvalidLocationException() throws Exception {

		Mockito.when(reservationService.numSeatsAvailable(Mockito.anyLong(), Mockito.anyLong()))
				.thenThrow(InvalidLocationException.class);
		;

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/numSeatsAvailable/1/1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}


	/**
	 * This is test case to validate /numSeatsAvailable end point when invalid 
	 * event id is sent as part of request.
	 * @throws Exception
	 */
	@Test
	public void testNumOfSeatsAvailableInvalidEventException() throws Exception {
		Mockito.when(reservationService.numSeatsAvailable(Mockito.anyLong(), Mockito.anyLong()))
				.thenThrow(InvalidEventException.class);
		;

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/numSeatsAvailable/1/1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}

	/**
	 * This is test to validate /findAndHoldSeats end point
	 * It makes sure service returning same response that is sent by 
	 * Reservation service
	 * @throws Exception
	 */
	@Test
	public void findAndHoldSeatsTest() throws Exception {

		Mockito.when(reservationService.findAndHoldSeats(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt(),
				Mockito.anyString())).thenReturn(seatHold);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/findAndHoldSeats")
				.accept(MediaType.APPLICATION_JSON).content(FIND_AND_HOLD_SEATS_REQUEST)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(FIND_AND_HOLD_SEATS_RESPONSE, result.getResponse().getContentAsString(), false);

	}

	/**
	 * This is test case to validate /findAndHoldSeats end point when invalid 
	 * user  id is sent as part of request.
	 * 
	 * @throws Exception
	 */
	@Test
	public void findAndHoldSeatsInvalidUserIDTest() throws Exception {

		Mockito.when(reservationService.findAndHoldSeats(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt(),
				Mockito.anyString())).thenThrow(InvalidUserException.class);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/findAndHoldSeats")
				.accept(MediaType.APPLICATION_JSON).content(FIND_AND_HOLD_SEATS_REQUEST)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

	}
	/**
	 * This is test case to validate /findAndHoldSeats end point when invalid 
	 * Location  id is sent as part of request.
	 * 
	 * @throws Exception
	 */
	@Test
	public void findAndHoldSeatsInvalidLocationIdTest() throws Exception {

		Mockito.when(reservationService.findAndHoldSeats(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt(),
				Mockito.anyString())).thenThrow(InvalidLocationException.class);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/findAndHoldSeats")
				.accept(MediaType.APPLICATION_JSON).content(FIND_AND_HOLD_SEATS_REQUEST)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

	}
	/**
	 * This is test case to validate /findAndHoldSeats end point when invalid 
	 * Event  id is sent as part of request.
	 * 
	 * @throws Exception
	 */
	@Test
	public void findAndHoldSeatsInvalidEventIdTest() throws Exception {

		Mockito.when(reservationService.findAndHoldSeats(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt(),
				Mockito.anyString())).thenThrow(InvalidEventException.class);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/findAndHoldSeats")
				.accept(MediaType.APPLICATION_JSON).content(FIND_AND_HOLD_SEATS_REQUEST)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

	}
	/**
	 * This is test case to validate /findAndHoldSeats end point when ReservationService 
	 *  throws NotEnoughSeatsAvailableToHoldException
	 * 
	 * @throws Exception
	 */
	@Test
	public void findAndHoldSeatsNotEnoughSeatsAvailableToHoldExceptionTest() throws Exception {

		Mockito.when(reservationService.findAndHoldSeats(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt(),
				Mockito.anyString())).thenThrow(NotEnoughSeatsAvailableToHoldException.class);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/findAndHoldSeats")
				.accept(MediaType.APPLICATION_JSON).content(FIND_AND_HOLD_SEATS_REQUEST)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}

	/**
	 * This is Test case to validate /reserveSeats end point
	 * It makes sure service returning same response that is sent by 
	 * Reservation service
	 * @throws Exception
	 * @throws Exception
	 */
	@Test
	public void reserveSeatsTest() throws Exception {

		Mockito.when(reservationService.reserveSeats(Mockito.anyLong(), Mockito.anyString()))
				.thenReturn(reservationResponse);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/reserveSeats").accept(MediaType.APPLICATION_JSON)
				.content(RESERVE_SEATS_REQUEST).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(RESERVE_SEATS_RESPONSE, result.getResponse().getContentAsString(), false);

	}
	/**
	 * This is test case to validate /reserveSeats end point when invalid 
	 * User id is sent as part of request.
	 * 
	 * @throws Exception
	 */
	@Test
	public void reserveSeatsInvalidUserExceptionTest() throws Exception {

		Mockito.when(reservationService.reserveSeats(Mockito.anyLong(), Mockito.anyString()))
				.thenThrow(InvalidUserException.class);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/reserveSeats").accept(MediaType.APPLICATION_JSON)
				.content(RESERVE_SEATS_REQUEST).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

	}
	
	/**
	 * This is test case to validate /reserveSeats end point when invalid 
	 * Hold id is sent as part of request.
	 * 
	 * @throws Exception
	 */
	@Test
	public void reserveSeatsInvalidHoldIDExceptionTest() throws Exception {

		Mockito.when(reservationService.reserveSeats(Mockito.anyLong(), Mockito.anyString()))
				.thenThrow(InvalidHoldIDException.class);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/reserveSeats").accept(MediaType.APPLICATION_JSON)
				.content(RESERVE_SEATS_REQUEST).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

	}
	
	/**
	 * This is test case to validate /reserveSeats end point when Reservation
	 * service throws ExpiredHoldIdRequestedException
	 * 
	 * @throws Exception
	 */
	@Test
	public void reserveSeatsExpiredHoldIdRequestedExceptionTest() throws Exception {

		Mockito.when(reservationService.reserveSeats(Mockito.anyLong(), Mockito.anyString()))
				.thenThrow(ExpiredHoldIdRequestedException.class);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/reserveSeats").accept(MediaType.APPLICATION_JSON)
				.content(RESERVE_SEATS_REQUEST).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}
	/**
	 * This is test case to validate /reserveSeats end point when Reservation
	 * service throws ExpiredHoldIdRequestedException
	 * 
	 * @throws Exception
	 */
	@Test
	public void reserveSeatsHoldIdAlreadyUsedForReservationExceptionTest() throws Exception {

		Mockito.when(reservationService.reserveSeats(Mockito.anyLong(), Mockito.anyString()))
				.thenThrow(HoldIdAlreadyUsedForReservationException.class);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/reserveSeats").accept(MediaType.APPLICATION_JSON)
				.content(RESERVE_SEATS_REQUEST).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}

}
