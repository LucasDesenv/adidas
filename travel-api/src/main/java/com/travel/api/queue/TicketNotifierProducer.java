package com.travel.api.queue;

import com.google.gson.Gson;
import com.travel.api.domain.TicketData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lusouza on 22/07/18.
 */
@Component
public class TicketNotifierProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketNotifierProducer.class);

    @Autowired
    private TicketQueueConfiguration ticketQueueConfiguration;

    private final RabbitTemplate rabbitTemplate;

    TicketNotifierProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void notify(TicketData ticket){
        LOGGER.info(String.format("Producing message to Ticket: %s", ticket.getId()));
        rabbitTemplate.convertAndSend(ticketQueueConfiguration.getExchangeName(),
                ticketQueueConfiguration.getQueueNotifierName(), new Gson().toJson(ticket));
    }
}
