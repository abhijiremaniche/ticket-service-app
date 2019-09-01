package com.walmart.ticketservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.walmart.ticketservice.entities.User;

/**
 * @author Aremaniche 
 * This is repository class for User entity .
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	public User findByusername(String username);

}
