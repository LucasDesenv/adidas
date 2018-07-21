package com.ticket.calculator.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by lusouza on 20/07/18.
 */
@FeignClient(name = "travel-api")
public interface TicketClient {
    @GetMapping("/ping")
    public String ping();
}
