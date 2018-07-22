package com.ticket.calculator.service;

import com.ticket.calculator.client.TicketClient;
import com.ticket.calculator.dto.TicketDTO;
import com.ticket.calculator.exception.DataNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by lusouza on 21/07/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class CalculatorServiceTest {

    @InjectMocks
    private CalculatorServiceImpl calculatorService;
    @Mock
    private TicketClient ticketClient;
    @Rule
    public ExpectedException expectedException =  ExpectedException.none();

    @Test
    public void shouldReturnTheNonStoppingWay(){
        TicketDTO ticket = new TicketDTO();
        ticket.setOriginCity("Barcelona");
        ticket.setDestinyCity("Madrid");
        ticket.setDepartureTime(LocalTime.of(10,30));
        ticket.setArriveTime(LocalTime.of(7,30));

        when(ticketClient.
                searchByOriginAndDestinyCity("Barcelona", "Madrid")).
                thenReturn(Arrays.asList(ticket));
        TicketDTO ticketDTO = calculatorService.calculateShortestWay("Barcelona", "Madrid");
        assertThat(ticketDTO.getOriginCity()).isEqualTo(ticket.getOriginCity());
        assertThat(ticketDTO.getDestinyCity()).isEqualTo(ticket.getDestinyCity());
        assertThat(ticketDTO.getDepartureTime()).isEqualTo(ticket.getDepartureTime());
        assertThat(ticketDTO.getArriveTime()).isEqualTo(ticket.getArriveTime());
        assertThat(ticketDTO.getConnection()).isNull();
    }

    @Test
    public void shouldReturnWithConnection(){
        TicketDTO ticket = new TicketDTO();
        ticket.setOriginCity("Criciuma");
        ticket.setDestinyCity("Florianopolis");
        ticket.setDepartureTime(LocalTime.of(10,30));
        ticket.setArriveTime(LocalTime.of(7,30));

        TicketDTO ticket2 = new TicketDTO();
        ticket2.setOriginCity("Florianopolis");
        ticket2.setDestinyCity("Sao Paulo");
        ticket2.setDepartureTime(LocalTime.of(10,30));
        ticket2.setArriveTime(LocalTime.of(7,30));


        when(ticketClient.
                searchByOriginAndDestinyCity("Criciuma", "Sao Paulo")).
                thenReturn(Collections.emptyList());
        when(ticketClient.
                searchByOriginAndDestinyCity(null, "Sao Paulo")).
                thenReturn(Arrays.asList(ticket2));
        when(ticketClient.
                searchByOriginAndDestinyCity("Criciuma", "Florianopolis")).
                thenReturn(Arrays.asList(ticket));

        TicketDTO ticketDTO = calculatorService.calculateShortestWay("Criciuma", "Sao Paulo");
        assertThat(ticketDTO).isEqualToComparingFieldByField(ticket);
        assertThat(ticketDTO.getConnection()).isEqualToComparingFieldByField(ticket2);
    }

    @Test
    public void shouldThrowExceptionIfThereIsNoWayToTheDestiny(){
        when(ticketClient.
                searchByOriginAndDestinyCity("Criciuma", "Sao Paulo")).
                thenReturn(Collections.emptyList());
        when(ticketClient.
                searchByOriginAndDestinyCity(null, "Sao Paulo")).
                thenReturn(Collections.emptyList());
        expectedException.expect(DataNotFoundException.class);
        expectedException.expectMessage("No tickets found for this destiny.");
        calculatorService.calculateShortestWay("Criciuma", "Sao Paulo");
    }

    @Test
    public void shouldReturnWithTheShortestConnection(){
        TicketDTO ticket = new TicketDTO();
        ticket.setOriginCity("Criciuma");
        ticket.setDestinyCity("Florianopolis");
        ticket.setDepartureTime(LocalTime.of(10,30));
        ticket.setArriveTime(LocalTime.of(7,30));

        TicketDTO ticket2 = new TicketDTO();
        ticket2.setOriginCity("Florianopolis");
        ticket2.setDestinyCity("Sao Paulo");
        ticket2.setDepartureTime(LocalTime.of(10,30));
        ticket2.setArriveTime(LocalTime.of(7,30));

        TicketDTO ticket3 = new TicketDTO();
        ticket3.setOriginCity("Florianopolis");
        ticket3.setDestinyCity("Sao Paulo");
        ticket3.setDepartureTime(LocalTime.of(7,0));
        ticket3.setArriveTime(LocalTime.of(18,0));


        when(ticketClient.
                searchByOriginAndDestinyCity("Criciuma", "Sao Paulo")).
                thenReturn(Collections.emptyList());
        when(ticketClient.
                searchByOriginAndDestinyCity(null, "Sao Paulo")).
                thenReturn(Arrays.asList(ticket2, ticket3));
        when(ticketClient.
                searchByOriginAndDestinyCity("Criciuma", "Florianopolis")).
                thenReturn(Arrays.asList(ticket));

        TicketDTO ticketDTO = calculatorService.calculateShortestWay("Criciuma", "Sao Paulo");
        assertThat(ticketDTO).isEqualToComparingFieldByField(ticket);
        assertThat(ticketDTO.getConnection()).isEqualToComparingFieldByField(ticket3);
    }

    @Test
    public void shouldReturnWithThreeConnections(){
        TicketDTO ticket = new TicketDTO();
        ticket.setOriginCity("Criciuma");
        ticket.setDestinyCity("Florianopolis");
        ticket.setDepartureTime(LocalTime.of(10,30));
        ticket.setArriveTime(LocalTime.of(7,30));

        TicketDTO ticket2 = new TicketDTO();
        ticket2.setOriginCity("Florianopolis");
        ticket2.setDestinyCity("Sao Paulo");
        ticket2.setDepartureTime(LocalTime.of(10,30));
        ticket2.setArriveTime(LocalTime.of(7,30));

        TicketDTO ticket3 = new TicketDTO();
        ticket3.setOriginCity("Sao Paulo");
        ticket3.setDestinyCity("Rio de Janeiro");
        ticket3.setDepartureTime(LocalTime.of(7,0));
        ticket3.setArriveTime(LocalTime.of(18,0));

        TicketDTO ticket4 = new TicketDTO();
        ticket4.setOriginCity("Rio de Janeiro");
        ticket4.setDestinyCity("Salvador");
        ticket4.setDepartureTime(LocalTime.of(7,0));
        ticket4.setArriveTime(LocalTime.of(18,0));


        when(ticketClient.
                searchByOriginAndDestinyCity("Criciuma", "Salvador")).
                thenReturn(Collections.emptyList());
        when(ticketClient.
                searchByOriginAndDestinyCity(null, "Salvador")).
                thenReturn(Arrays.asList(ticket4));
        when(ticketClient.
                searchByOriginAndDestinyCity(null, "Rio de Janeiro")).
                thenReturn(Arrays.asList(ticket3));
        when(ticketClient.
                searchByOriginAndDestinyCity(null, "Sao Paulo")).
                thenReturn(Arrays.asList(ticket2));
        when(ticketClient.
                searchByOriginAndDestinyCity(null, "Florianopolis")).
                thenReturn(Arrays.asList(ticket));

        TicketDTO ticketDTO = calculatorService.calculateShortestWay("Criciuma", "Salvador");
        assertThat(ticketDTO).isEqualToComparingFieldByField(ticket);
        assertThat(ticketDTO.getConnection()).isEqualToComparingFieldByField(ticket3);
        assertThat(ticketDTO.getConnection().getConnection()).isEqualToComparingFieldByField(ticket2);
        assertThat(ticketDTO.getConnection().getConnection().getConnection()).isEqualToComparingFieldByField(ticket3);
        assertThat(ticketDTO.getConnection().getConnection().getConnection().getConnection()).isEqualToComparingFieldByField(ticket4);
    }
}
