package com.walmart.ticketservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.walmart.ticketservice.entities.Event;

/**
 * @author Aremaniche 
 * This is repository class for Event entity .
 */
@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

}
