package com.travel.api.queue;

import com.google.gson.Gson;
import com.travel.api.domain.TicketData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.travel.api.queue.TicketQueueConfiguration.EXCHANGE;
import static com.travel.api.queue.TicketQueueConfiguration.QUEUE_NOTIFIER;

/**
 * Created by lusouza on 22/07/18.
 */
@Component
public class TicketNotifierProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketNotifierProducer.class);

    private final RabbitTemplate rabbitTemplate;

    TicketNotifierProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void notify(TicketData ticket){
        LOGGER.info(String.format("Producing message to Ticket: %s", ticket.getId()));
        rabbitTemplate.convertAndSend(EXCHANGE, QUEUE_NOTIFIER, new Gson().toJson(ticket));
    }
}
