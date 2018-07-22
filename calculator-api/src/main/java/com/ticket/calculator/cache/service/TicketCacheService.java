package com.ticket.calculator.cache.service;

import com.ticket.calculator.dto.TicketDTO;

import java.util.List;
import java.util.Optional;

/**
 * Created by lusouza on 22/07/18.
 */
public interface TicketCacheService {

    void save(TicketDTO ticketDTO);

    List<TicketDTO> searchByOriginAndDestinyCity(Optional<String> originCity, Optional<String> destinyCity);
}
