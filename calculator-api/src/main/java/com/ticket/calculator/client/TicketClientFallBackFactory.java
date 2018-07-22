package com.ticket.calculator.client;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lusouza on 22/07/18.
 */
@Component
public class TicketClientFallBackFactory implements FallbackFactory<TicketClient>{
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketClientFallBackFactory.class);
    @Autowired
    private TicketClientFallBack ticketClientFallBack;
    @Override
    public TicketClient create(Throwable throwable) {
        /*
         * There is a bug in Feign that call this method on startup,
         * so the log error will be called unnecessary.
         * https://github.com/spring-cloud/spring-cloud-netflix/issues/1471
         */
        final boolean isBugFromFeign = throwable.getMessage() == null && throwable instanceof RuntimeException;
        if (isBugFromFeign){
            LOGGER.warn("Feign bug.");
            return ticketClientFallBack;
        }
        LOGGER.error(String.format("The circuit is open, watch out! Real message: %s", throwable.getMessage()), throwable);
        return ticketClientFallBack;
    }
}
