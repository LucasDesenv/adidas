package com.ticket.calculator.service;

import com.ticket.calculator.client.TicketClient;
import com.ticket.calculator.dto.TicketDTO;
import com.ticket.calculator.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Created by lusouza on 21/07/18.
 */
@Service
public class CalculatorServiceImpl implements CalculatorService {
    @Autowired
    private TicketClient ticketClient;

    @Override
    public TicketDTO calculateShortestWay(String fromCity, String toCity) {
        final List<TicketDTO> nonStoppingTicket = getNonStoppingTicket(fromCity, toCity);

        if (!nonStoppingTicket.isEmpty()){
            return nonStoppingTicket.stream().sorted(Comparator.comparing(TicketDTO::getArriveTime)).findFirst().get();
        }

        return getWithConnection(fromCity, toCity);
    }

    private TicketDTO getWithConnection(String fromCity, String toCity) {
        final List<TicketDTO> ticketsToTheDestiny = getTicketsToDestiny(toCity);
        if (ticketsToTheDestiny.isEmpty()){
            throw new DataNotFoundException("No tickets found for this destiny.");
        }

        ticketsToTheDestiny.forEach(destiny -> {
            final List<TicketDTO> ticketsToTheConnectionOriginCity = getNonStoppingTicket(fromCity, destiny.getOriginCity());
            if (!ticketsToTheConnectionOriginCity.isEmpty()){
                ticketsToTheConnectionOriginCity.sort(Comparator.comparing(TicketDTO::getArriveTime));
                final TicketDTO shortestConnection = ticketsToTheConnectionOriginCity.stream().findFirst().get();
                destiny.setConnection(shortestConnection);
            }
        });

        final TicketDTO shortestConnection = ticketsToTheDestiny.stream().filter(t -> t.getConnection() != null)
                .sorted(Comparator.comparing(t -> t.getConnection().getArriveTime()))
                .findFirst()
                .orElse(null);
        if (shortestConnection != null){
            return shortestConnection;
        }

        throw new DataNotFoundException("No connections found for this destiny and origin.");

    }

    private List<TicketDTO> getTicketsToDestiny(String toCity) {
        return ticketClient.searchByOriginAndDestinyCity(null, toCity);
    }

    private List<TicketDTO> getNonStoppingTicket(String fromCity, String toCity) {
        return ticketClient.searchByOriginAndDestinyCity(fromCity, toCity);
    }
}
