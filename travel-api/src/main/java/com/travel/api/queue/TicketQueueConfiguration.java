package com.travel.api.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lusouza on 22/07/18.
 */
@Configuration
public class TicketQueueConfiguration {
    static final String QUEUE_NOTIFIER = "notifier";
    static final String EXCHANGE = "ticket_events";

    @Bean
    Queue notifier(){
        return new Queue(QUEUE_NOTIFIER, true);
    }

    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(EXCHANGE, true, false);
    }

    @Bean
    Binding bindingPeopleRead(@Qualifier("notifier") Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with(QUEUE_NOTIFIER);
    }

}
