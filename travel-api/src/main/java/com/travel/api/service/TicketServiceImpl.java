package com.travel.api.service;

import com.travel.api.domain.Ticket;
import com.travel.api.domain.TicketData;
import com.travel.api.exception.BadDataException;
import com.travel.api.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by lusouza on 20/07/18.
 */
@Service
public class TicketServiceImpl implements TicketService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public TicketData save(TicketData ticketData) throws BadDataException {
        try{
            Ticket ticket = new Ticket(ticketData);
            ticket.prepareToSave();
            Ticket ticketSaved = ticketRepository.save(ticket);
            return ticketSaved;
        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage(), e);
            throw new BadDataException(e.getMessage());
        }
    }

    @Override
    public List<? extends TicketData> searchByCity(Optional<String> originCity, Optional<String> destinyCity) {
        if (originCity.isPresent() && destinyCity.isPresent()){
            return ticketRepository.findByOriginCityAndDestinyCity(originCity.get(),destinyCity.get());
        }

        if (destinyCity.isPresent()){
            return ticketRepository.findByDestinyCity(destinyCity.get());
        }

        if (originCity.isPresent()){
            return ticketRepository.findByOriginCity(originCity.get());
        }

        return Collections.emptyList();
    }
}
