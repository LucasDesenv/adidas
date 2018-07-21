package com.ticket.calculator.client;

import com.ticket.calculator.dto.TicketDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by lusouza on 20/07/18.
 */
@FeignClient(name = "travel-api")
public interface TicketClient {
    @GetMapping("/ping")
    String ping();
    @GetMapping("/v1/api/ticket/search")
    List<TicketDTO> searchByOriginAndDestinyCity(@RequestParam(name = "originCity", required = false) String originCity,
                                                                 @RequestParam(name = "destinyCity", required = false) String destinyCity);
}
