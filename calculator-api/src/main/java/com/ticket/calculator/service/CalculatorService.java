package com.ticket.calculator.service;

import com.ticket.calculator.dto.TicketDTO;

/**
 * Created by lusouza on 21/07/18.
 */
public interface CalculatorService {
    public TicketDTO calculateShortestWay(String fromCity, String toCity);
}
