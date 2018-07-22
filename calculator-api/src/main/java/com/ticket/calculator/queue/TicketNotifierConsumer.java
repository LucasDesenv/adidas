package com.ticket.calculator.queue;

import com.google.gson.Gson;
import com.ticket.calculator.cache.service.TicketCacheService;
import com.ticket.calculator.dto.TicketDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


/**
 * Created by lusouza on 22/07/18.
 */
@Component
public class TicketNotifierConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketNotifierConsumer.class);

    @Autowired
    private TicketCacheService ticketCacheService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value="notifier",durable = "True"),
                    exchange = @Exchange(value = "ticket_events", durable = "True")
            ))
    public void processMessage(org.springframework.amqp.core.Message message, @Payload String ticketJson){
        TicketDTO ticket = new Gson().fromJson(ticketJson, TicketDTO.class);
        LOGGER.info(String.format("Consuming the message to cache the ticket received: %s", ticket.getId()));
        ticketCacheService.save(ticket);
    }
}
