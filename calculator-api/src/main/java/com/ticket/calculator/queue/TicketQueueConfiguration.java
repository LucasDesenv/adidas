package com.ticket.calculator.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lusouza on 22/07/18.
 */
@Configuration
public class TicketQueueConfiguration {
    final String queueNotifierName;
    final String exchangeName;

    TicketQueueConfiguration(@Value("${ticket.rabbit.notifier.queue-name}") String queue,
                             @Value("${ticket.rabbit.notifier.exchange-name}") String exchange){
        queueNotifierName = queue;
        exchangeName = exchange;
    }

    @Bean
    Queue notifier(){
        return new Queue(queueNotifierName, true);
    }

    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    Binding bindingPeopleRead(@Qualifier("notifier") Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with(queueNotifierName);
    }

}
