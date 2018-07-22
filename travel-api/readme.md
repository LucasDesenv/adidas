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
* RabbitMQ
    * Used to notify when a Ticket is updated.
    * There is an Exchange called _ticket_events_ and a Queue called _notifier_.
    * The queue is marked as _durable_, since we don't wanna lose any message if Rabbit is down or crash.
    
**STEPS** (Eureka server should be started before this)

* 1 - **MongoDB**
    * Install mongodb: https://docs.mongodb.com/manual/installation/
    * Start it: _$ sudo mongod_ (linux way)
    * Make sure it started at 127.0.0.1:27017 by executing 
        * _$ sudo mongo --host 127.0.0.1:27017_

* 2 - **RabbitMQ**.
    * Install it: _https://www.rabbitmq.com/install-homebrew.html_
    * Add the _/usr/local/sbin/_ to your bash_profile and restart the terminal.
    * Start the service by executing _rabbitmq-server_
    * To check the status: _rabbitmqctl status_
    * To check messages: _rabbitmqadmin get queue=notifier requeue=true count=10_
    * To list all queues: _rabbitmqadmin list queues_
    * To list all exchanges: _rabbitmqadmin list exchanges_
* 3 - Run the class `TravelApplicationBoot.java`.

* 4 - It will be available at `http://localhost:7111/swagger-ui.html`

**TODO in future**
* Change Rabbit to Kafka for **topic** usage to notify.


**REFERENCES**

* **SWAGGER**
  * http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api

* **MONGO**
    * https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/
    * https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo
    * https://github.com/eugenp/tutorials/tree/master/spring-boot/src/test/java/com/baeldung/mongodb

* **RabbitMQ**
    * https://www.rabbitmq.com/install-homebrew.html
    * https://stackoverflow.com/questions/23050120/rabbitmq-command-doesnt-exist
    * https://www.rabbitmq.com/tutorials/tutorial-two-python.html
    * https://www.rabbitmq.com/management-cli.html