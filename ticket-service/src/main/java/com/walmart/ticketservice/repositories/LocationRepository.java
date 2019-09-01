package com.walmart.ticketservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.walmart.ticketservice.entities.Location;

/**
 * @author Aremaniche 
 * This is repository class for Location entity .
 */
@Repository
public interface LocationRepository  extends   CrudRepository<Location, Long> {

}
