package com.ticket.calculator.client;

import com.ticket.calculator.cache.service.TicketCacheService;
import com.ticket.calculator.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Created by lusouza on 22/07/18.
 */
@Component()
public class TicketClientFallBack implements TicketClient{
    @Autowired
    private TicketCacheService ticketCacheService;

    @Override
    public String ping() {
        return "pong from fallback";
    }

    @Override
    public List<Ticket> searchByOriginAndDestinyCity(String originCity, String destinyCity) {
        return ticketCacheService.searchByOriginAndDestinyCity(Optional.ofNullable(originCity), Optional.ofNullable(destinyCity));
    }
}
