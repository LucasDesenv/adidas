package com.ticket.calculator.service;

import com.ticket.calculator.client.TicketClient;
import com.ticket.calculator.domain.Ticket;
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
    public Ticket calculateShortestWay(String fromCity, String toCity) {
        final List<Ticket> nonStoppingTicket = getNonStoppingTicket(fromCity, toCity);

        if (!nonStoppingTicket.isEmpty()){
            return nonStoppingTicket.stream().sorted().findFirst().get();
        }

        return getWithConnection(fromCity, toCity);
    }

    private Ticket getWithConnection(String fromCity, String toCity) {
        final List<Ticket> ticketsToDestiny = getTicketsToDestiny(toCity);
        if (ticketsToDestiny.isEmpty()){
            throw new DataNotFoundException("No tickets found for this destiny.");
        }

        final Optional<Ticket> shortestConnection = findShortestConnection(fromCity, ticketsToDestiny);
        return shortestConnection.orElseThrow(() -> new DataNotFoundException("No connections found for this destiny and origin."));
    }

    private Optional<Ticket> findShortestConnection(String fromCity, List<Ticket> ticketsToDestiny) {
        final List<Ticket> ticketsWithConnections = new ArrayList<>();

        ticketsToDestiny.forEach(ticketToDestiny -> {
            final List<Ticket> ticketsToTheConnection = getNonStoppingTicket(fromCity, ticketToDestiny.getOriginCity());
            if (!ticketsToTheConnection.isEmpty()){
                ticketsToTheConnection.sort(Ticket::compareTo);
                final Ticket shortestFirstTicket = ticketsToTheConnection.stream().findFirst().get();
                shortestFirstTicket.setConnection(ticketToDestiny);
                ticketsWithConnections.add(shortestFirstTicket);
            }
        });

        if (ticketsWithConnections.isEmpty()){
            ticketsWithConnections.addAll(findLastTryToLongWayToTheDestiny(fromCity, ticketsToDestiny));
        }

        final Optional<Ticket> shortestConnection = calculateTheShortestWithConnection(ticketsWithConnections);

        return shortestConnection;
    }

    private Optional<Ticket> calculateTheShortestWithConnection(List<Ticket> ticketsWithConnections) {
        return ticketsWithConnections.stream()
                    .filter(Ticket::hasConnection)
                    .sorted()
                    .sorted(Comparator.comparing(t -> t.getConnection().getDepartureTime()))
                    .findFirst();
    }

    private List<Ticket> findLastTryToLongWayToTheDestiny(String fromCity, List<Ticket> ticketsToDestiny) {
        final List<Ticket> ticketsWithConnections = new LinkedList<>();

        ticketsToDestiny.forEach(ticketToDestiny -> {
            final List<Ticket> ticketsToTheConnection = getTicketsToDestiny(ticketToDestiny.getOriginCity());

            ticketsToTheConnection.forEach(middleConnection -> {
                final List<Ticket> ticketsToTheMiddleConnection = getNonStoppingTicket(fromCity, middleConnection.getOriginCity());
                if (!ticketsToTheMiddleConnection.isEmpty()){
                    ticketsToTheMiddleConnection.sort(Comparator.comparing(Ticket::getDepartureTime));
                    final Ticket shortestFirstTicket = ticketsToTheMiddleConnection.stream().findFirst().get().copy();

                    final Ticket middleConnectionCopy = middleConnection.copy();
                    middleConnectionCopy.setConnection(ticketToDestiny);
                    shortestFirstTicket.setConnection(middleConnectionCopy);
                    ticketsWithConnections.add(shortestFirstTicket);
                }
            });
        });
        return ticketsWithConnections;
    }

    private List<Ticket> getTicketsToDestiny(String toCity) {
        return ticketClient.searchByOriginAndDestinyCity(null, toCity);
    }

    private List<Ticket> getNonStoppingTicket(String fromCity, String toCity) {
        return ticketClient.searchByOriginAndDestinyCity(fromCity, toCity);
    }
}
