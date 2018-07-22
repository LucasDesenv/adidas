# **Travel Server Api**

This Application is the Server that will be accessed by the Travel Microservice in order 
to search for the ways from a city to another one.

**ARCHITECTURE**
* Spring Boot.
    * It's a great framework for microservice architecture, since it's really easy
    to configure, test, deploy and has many support from the community.
* MongoDB.
    * NoSQL database based on documents. Highly availability and scalability 
    makes it great for microservices environment.
* SWAGGER
    * Used for API documentation.  
    
**STEPS** (Eureka server should be started before this)

* 1 - **MongoDB**
    * Install mongodb: https://docs.mongodb.com/manual/installation/
    * Start it: _$ sudo mongod_ (linux way)
    * Make sure it started at 127.0.0.1:27017 by executing 
        * _$ sudo mongo --host 127.0.0.1:27017_

* 2 - Run the application by executing **TravelApplicationBoot**.


**REFERENCES**

* **SWAGGER**
  * http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api

* **MONGO**
    * https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/
    * https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo
    * https://github.com/eugenp/tutorials/tree/master/spring-boot/src/test/java/com/baeldung/mongodb


