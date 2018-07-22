package com.ticket.calculator.client;

import com.ticket.calculator.dto.TicketDTO;

import java.util.List;

/**
 * Created by lusouza on 22/07/18.
 */
public class TicketClientFallBack implements TicketClient{
    @Override
    public String ping() {
        return "pong from fallback";
    }

    @Override
    public List<TicketDTO> searchByOriginAndDestinyCity(String originCity, String destinyCity) {
        return null;
    }
}
