package com.ticket.calculator.service;

import com.ticket.calculator.domain.Ticket;

/**
 * Created by lusouza on 21/07/18.
 */
public interface CalculatorService {
    public Ticket calculateShortestWay(String fromCity, String toCity);
}
