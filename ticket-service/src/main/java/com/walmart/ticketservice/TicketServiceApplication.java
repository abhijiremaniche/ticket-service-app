package com.walmart.ticketservice;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Aremaniche 
 * This is main spring boot application class.It starts the
 * spring boot application.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TicketServiceApplication {

	@Autowired
	DataSource dataSource;

	/**
	 * This is main method for spring boot application.
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(TicketServiceApplication.class, args);
	}

}
