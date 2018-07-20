package com.travel.api.service;

import com.travel.api.domain.TicketData;
import com.travel.api.exception.BadDataException;

import java.util.List;
import java.util.Optional;

/**
 * Created by lusouza on 20/07/18.
 */
public interface TicketService {
    TicketData save(TicketData ticketData) throws BadDataException;

    List<? extends TicketData> searchByCity(Optional<String> originCity, Optional<String> destinyCity);
}
