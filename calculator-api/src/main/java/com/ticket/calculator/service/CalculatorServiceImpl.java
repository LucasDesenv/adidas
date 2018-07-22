package com.ticket.calculator.service;

import com.ticket.calculator.client.TicketClient;
import com.ticket.calculator.dto.TicketDTO;
import com.ticket.calculator.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            return nonStoppingTicket.stream().sorted(Comparator.comparing(TicketDTO::getDepartureTime)).findFirst().get();
        }

        return getWithConnection(fromCity, toCity);
    }

    private TicketDTO getWithConnection(String fromCity, String toCity) {
        final List<TicketDTO> connections = getTicketsToDestiny(toCity);
        if (connections.isEmpty()){
            throw new DataNotFoundException("No tickets found for this destiny.");
        }

        final List<TicketDTO> ticketsWithConnections = new ArrayList<>();

        connections.forEach(connection -> {
            final List<TicketDTO> mainTickets = getNonStoppingTicket(fromCity, connection.getOriginCity());
            if (!mainTickets.isEmpty()){
                mainTickets.sort(Comparator.comparing(TicketDTO::getDepartureTime));
                final TicketDTO shortesFirstTicket = mainTickets.stream().findFirst().get();
                shortesFirstTicket.setConnection(connection);
                ticketsWithConnections.add(shortesFirstTicket);
            }
        });

        final TicketDTO shortestConnection = ticketsWithConnections.stream().filter(t -> t.getConnection() != null)
                .sorted(Comparator.comparing(t -> t.getDepartureTime()))
                .sorted(Comparator.comparing(t -> t.getConnection().getDepartureTime()))
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
