# **Calculator Client API**

This Application is the Client that will access the Travel Microservice in order 
to calculate the shortest way from a city to another one.

**ARCHITECTURE**
* Spring Boot.
    * It's a great framework for microservice architecture, since it's really easy
    to configure, test, deploy and has many support from the community.
* Feign.
    * It is a declarative web service client. It makes writing web service clients easier.
    The great thing here is that Feign integrates Ribbon and Eureka to provide a load balanced http client.
* Ribbon
    * For Load Balance. It's encapsulated in Feign.
* Hystix
    * For Fallback. It's encapsulated in Feign.
* Redis
    * Use for caching the tickets.
* RabbitMQ
    * Use to consume the queue `ticket_events.notifier` to save the data in cache. 
    So when the app is offline, the Hystrix will go to the caching.      
* SWAGGER
    * Used for API documentation.  
    
**STEPS** (Eureka server should be started before this)

* 1 - **Redis**
    * Download it from: https://redis.io/download or https://medium.com/@petehouston/install-and-config-redis-on-mac-os-x-via-homebrew-eb8df9a4f298
    * Go to the directory installed.
    * Execute _src/redis-server_ in my case: _$/Development/redis-4.0.10/src/redis-server_
    * You should see logging **'#Server initialized'** and **'Port: 6379'**

* 2 - **RabbitMQ** should be up. Read the **readme of travel-api**.

* 3 - Run the class `CalculatorApplicationBoot.java`

* 4 - It will be available at `http://localhost:8087/swagger-ui.html`



**REFERENCES**

* **LOAD BALANCE RIBBON** 
  * https://spring.io/guides/gs/client-side-load-balancing/

* **CIRCUIT BREAKER (HYSTRIX)** 
  * https://spring.io/guides/gs/circuit-breaker/
  * https://github.com/spring-guides/gs-circuit-breaker/tree/master/complete

* **FEIGN**
    * https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html
    * https://github.com/spring-cloud/spring-cloud-netflix/issues/1471

* **SWAGGER**
  * http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api

* **REDIS**
  * https://github.com/rgl/redis/downloads
  * http://www.baeldung.com/spring-data-redis-tutorial
  * https://examples.javacodegeeks.com/enterprise-java/spring/spring-data-redis-example/



