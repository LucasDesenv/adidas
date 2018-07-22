package com.ticket.calculator.service;

import com.ticket.calculator.client.TicketClient;
import com.ticket.calculator.dto.TicketDTO;
import com.ticket.calculator.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        final List<TicketDTO> ticketsToDestiny = getTicketsToDestiny(toCity);
        if (ticketsToDestiny.isEmpty()){
            throw new DataNotFoundException("No tickets found for this destiny.");
        }

        final Optional<TicketDTO> shortestConnection = findShortestConnection(fromCity, ticketsToDestiny);
        return shortestConnection.orElseThrow(() -> new DataNotFoundException("No connections found for this destiny and origin."));
    }

    private Optional<TicketDTO> findShortestConnection(String fromCity, List<TicketDTO> ticketsToDestiny) {
        final List<TicketDTO> ticketsWithConnections = new ArrayList<>();

        ticketsToDestiny.forEach(ticketToDestiny -> {
            final List<TicketDTO> ticketsToTheConnection = getNonStoppingTicket(fromCity, ticketToDestiny.getOriginCity());
            if (!ticketsToTheConnection.isEmpty()){
                ticketsToTheConnection.sort(Comparator.comparing(TicketDTO::getDepartureTime));
                final TicketDTO shortestFirstTicket = ticketsToTheConnection.stream().findFirst().get();
                shortestFirstTicket.setConnection(ticketToDestiny);
                ticketsWithConnections.add(shortestFirstTicket);
            }
        });

        if (ticketsWithConnections.isEmpty()){
            ticketsWithConnections.addAll(findLastTryToLongWayToTheDestiny(fromCity, ticketsToDestiny));
        }

        final Optional<TicketDTO> shortestConnection = calculateTheShortestWithConnection(ticketsWithConnections);

        return shortestConnection;
    }

    private Optional<TicketDTO> calculateTheShortestWithConnection(List<TicketDTO> ticketsWithConnections) {
        return ticketsWithConnections.stream().filter(t -> t.getConnection() != null)
                    .sorted(Comparator.comparing(t -> t.getDepartureTime()))
                    .sorted(Comparator.comparing(t -> t.getConnection().getDepartureTime()))
                    .findFirst();
    }

    private List<TicketDTO> findLastTryToLongWayToTheDestiny(String fromCity, List<TicketDTO> ticketsToDestiny) {
        final List<TicketDTO> ticketsWithConnections = new LinkedList<>();

        ticketsToDestiny.forEach(ticketToDestiny -> {
            final List<TicketDTO> ticketsToTheConnection = getTicketsToDestiny(ticketToDestiny.getOriginCity());

            ticketsToTheConnection.forEach(middleConnection -> {
                final List<TicketDTO> ticketsToTheMiddleConnection = getNonStoppingTicket(fromCity, middleConnection.getOriginCity());
                if (!ticketsToTheMiddleConnection.isEmpty()){
                    ticketsToTheMiddleConnection.sort(Comparator.comparing(TicketDTO::getDepartureTime));
                    final TicketDTO shortestFirstTicket = ticketsToTheMiddleConnection.stream().findFirst().get().copy();

                    final TicketDTO middleConnectionCopy = middleConnection.copy();
                    middleConnectionCopy.setConnection(ticketToDestiny);
                    shortestFirstTicket.setConnection(middleConnectionCopy);
                    ticketsWithConnections.add(shortestFirstTicket);
                }
            });
        });
        return ticketsWithConnections;
    }

    private List<TicketDTO> getTicketsToDestiny(String toCity) {
        return ticketClient.searchByOriginAndDestinyCity(null, toCity);
    }

    private List<TicketDTO> getNonStoppingTicket(String fromCity, String toCity) {
        return ticketClient.searchByOriginAndDestinyCity(fromCity, toCity);
    }
}
