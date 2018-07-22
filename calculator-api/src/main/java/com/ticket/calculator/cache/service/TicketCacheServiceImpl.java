package com.ticket.calculator.cache.service;

import com.ticket.calculator.cache.repository.TicketCacheRepository;
import com.ticket.calculator.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by lusouza on 22/07/18.
 */
@Service
public class TicketCacheServiceImpl implements TicketCacheService{
    @Autowired
    private TicketCacheRepository ticketCacheRepository;

    @Override
    public void save(Ticket ticket) {
        ticketCacheRepository.save(ticket);
    }

    @Override
    public List<Ticket> searchByOriginAndDestinyCity(Optional<String> originCity, Optional<String> destinyCity) {
        if (originCity.isPresent() && destinyCity.isPresent()){
            return ticketCacheRepository.findByOriginCityAndDestinyCity(originCity.get(), destinyCity.get());
        }

        if (destinyCity.isPresent()){
            return ticketCacheRepository.findByDestinyCity(destinyCity.get());
        }

        return originCity.map(s -> ticketCacheRepository.findByOriginCity(s)).orElseGet(Collections::emptyList);

    }

}
