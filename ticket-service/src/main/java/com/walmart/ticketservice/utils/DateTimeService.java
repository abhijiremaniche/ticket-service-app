package com.walmart.ticketservice.utils;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;
/**
 * @author Aremaniche
 *
 */
@Component
public class DateTimeService {

	public Date getBusinessDate() {
		return new Date();
	}

	public Date addSeconds(Date date, Integer seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}

}
