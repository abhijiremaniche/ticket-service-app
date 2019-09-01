# Ticket Service 

### Problem Statement
Implement a simple ticket service that facilitates the discovery, temporary hold, and final reservation of seats within a high-demand performance venue.
     
Your homework assignment is to design and write a Ticket Service that provides the following functions:
- Find the number of seats available within the venue Note: available seats are seats that are neither held nor reserved.
- Find and hold the best available seats on behalf of a customerNote: each ticket hold should expire within a set number of seconds. 
- Reserve and commit a specific group of held seats for a customer

### Assumptions
  - Tickets will be reserved  or held in any order seat of numbers.
  - Customer is looking for availabilty ,seat hold and reservation for specific venue(location) and event. e.g. Location could any movie theater and event could be any movie at specific time (12pm,3pm,6pm,9pm).Location and Event will be always provided to check any availability seat holds.
  - Seat hold expiration timings are configurable in application and no notification  will be sent to user on expiry.
  - User looking for availabilty , seat hold and reservation are already authenticated  and have sufficient  privileges  to perform the actions.
  - Each venue does not have any levels. every seat at venue is at same level.
  - User is already registered with system in order to hold or reserve.
  
### Technolgy Stack
  - Java 1.8
  - Spring Boot
  - Spring Cloud Config
  - Spring Cloud/Netfix Eureka
  - Spring Cloud/Netfix Zuul
  - Spring Data JPA
  - Apache Derby 
  - Maven
  - Logback

### Project build Instrunctions
1.Create Database  with below scripts file
```
ticket-service-DDL.sql
```
2.Create Initial Data  with below script.This will set up one venue(location),Event and Seats at venue .
```
ticket-service-DML.sql
```
3.Clone a project from github
```
git clone
```
4 create local git repository with and add gateway-server, ticket-service folders with ticket-service.yml & gateway-server.yml
```
git init
```
 
5.Install Naming Server(Netfix Eureka):-
```sh
cd naming-service
mvn package
cd target
java -jar naming-service-0.0.1-SNAPSHOT.jar
```
6.Install Spring Cloud Config:-
```sh
cd config-server
mvn package
cd target
java -jar config-server-0.0.1-SNAPSHOT.jar
```

7.Install Netfix Zuul Server(gateway server):-
```sh
cd gateway-server
mvn package
cd target
java -jar gateway-server-0.0.1-SNAPSHOT.jar
```
8.Install ticket-service:-
```sh
cd ticket-service
mvn package
cd target
java -jar ticket-service-0.0.1-SNAPSHOT.jar
```
### Test Scenarios
###### Find the number of seats available within the venue
  - Service should   return correct number of seats for specified Location ID and event Id.
   - Service should also return valid error message when Location ID or event Id  is invalid.

###### Find and hold the best available seats for a customer
-  Service should hold requested number of seats for a customer.
-  Service should return valid error message when   user id is invalid.
-  Service should show valid error message when no seats are available to hold.
-  Service should not count hold seats as available seats
- Service should count expired seat hold as available seat.

###### Commit seats held for a specific customer
- Service should hold reserve seats based on seat hold id.
- Service should return valid error message when hold id is Invalid
- Service should return valid error message when hold id is Expired
- Service should return valid error message when dupliate hold id request is submitted.

###### Test Results
 To junit run below command
 ```sh
 mvn test
```
### Databse Design
  
### Application Services
##### Naming Service:
It mainly provides
- Service registration 
- Client lookup of service address 
- Information sharing between naming server nodes
- Health monitoring of micro service

##### Config  Service: 
- The config server helps to manage application configuration data in a cloud-based microservice application.
 
##### Gateway Service
Cross-cutting service concerns can be implemented in a single place without the individual development teams having to implement these concerns. Below are the cross-cutting service concerns which can be implemented at gateway
- Static routing
- Dynamic routing  
- Authentication and authorization 
- Metric collection and logging

##### Ticket Service 
This is main service which implements the Ticket Service application. It provides
mainly three services.
- Find the number of seats available within the venue
- Find and hold the best available seats for a customer
- Commit seats held for a specific customer

