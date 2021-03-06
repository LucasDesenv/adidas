package com.ticket.calculator.client;

import com.ticket.calculator.domain.Ticket;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by lusouza on 20/07/18.
 */
@FeignClient(name = "travel-api",
        fallbackFactory = TicketClientFallBackFactory.class)
public interface TicketClient {
    @GetMapping("/v1/api/ticket/ping")
    String ping();
    @GetMapping("/v1/api/ticket/search")
    List<Ticket> searchByOriginAndDestinyCity(@RequestParam(name = "originCity", required = false) String originCity,
                                              @RequestParam(name = "destinyCity", required = false) String destinyCity);
}
