package com.ticket.calculator.cache.service;

import com.ticket.calculator.domain.Ticket;

import java.util.List;
import java.util.Optional;

/**
 * Created by lusouza on 22/07/18.
 */
public interface TicketCacheService {

    void save(Ticket ticket);

    List<Ticket> searchByOriginAndDestinyCity(Optional<String> originCity, Optional<String> destinyCity);
}
