package com.ticket.calculator.service;

import com.ticket.calculator.client.TicketClient;
import com.ticket.calculator.domain.Ticket;
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
        Ticket ticket = new Ticket();
        ticket.setOriginCity("Barcelona");
        ticket.setDestinyCity("Madrid");
        ticket.setDepartureTime(LocalTime.of(10,30));
        ticket.setArriveTime(LocalTime.of(7,30));

        when(ticketClient.
                searchByOriginAndDestinyCity("Barcelona", "Madrid")).
                thenReturn(Arrays.asList(ticket));
        Ticket shortest = calculatorService.calculateShortestWay("Barcelona", "Madrid");
        assertThat(shortest).isEqualTo(ticket);
    }

    @Test
    public void shouldReturnTheNonStoppingWayBasedOnShortestTime(){
        Ticket ticket = new Ticket();
        ticket.setOriginCity("Barcelona");
        ticket.setDestinyCity("Madrid");
        ticket.setDepartureTime(LocalTime.of(10,30));
        ticket.setArriveTime(LocalTime.of(12,30));

        Ticket ticketLong = new Ticket();
        ticketLong.setOriginCity("Barcelona");
        ticketLong.setDestinyCity("Madrid");
        ticketLong.setDepartureTime(LocalTime.of(13,30));
        ticketLong.setArriveTime(LocalTime.of(15,30));

        when(ticketClient.
                searchByOriginAndDestinyCity("Barcelona", "Madrid")).
                thenReturn(Arrays.asList(ticket, ticketLong));
        Ticket shortest = calculatorService.calculateShortestWay("Barcelona", "Madrid");
        assertThat(shortest).isEqualTo(ticket);
    }

    @Test
    public void shouldReturnWithConnection(){
        Ticket ticket = new Ticket();
        ticket.setOriginCity("Criciuma");
        ticket.setDestinyCity("Florianopolis");
        ticket.setDepartureTime(LocalTime.of(10,30));
        ticket.setArriveTime(LocalTime.of(7,30));

        Ticket ticket2 = new Ticket();
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

        Ticket shortest = calculatorService.calculateShortestWay("Criciuma", "Sao Paulo");
        assertThat(shortest).isEqualTo(ticket);
        assertThat(shortest.getConnection()).isEqualTo(ticket2);
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
    public void shouldReturnWithTheShortestConnectionTime(){
        Ticket ticket = new Ticket();
        ticket.setOriginCity("Criciuma");
        ticket.setDestinyCity("Florianopolis");
        ticket.setDepartureTime(LocalTime.of(10,30));
        ticket.setArriveTime(LocalTime.of(11,30));

        Ticket ticketLong = new Ticket();
        ticketLong.setOriginCity("Criciuma");
        ticketLong.setDestinyCity("Florianopolis");
        ticketLong.setDepartureTime(LocalTime.of(12,30));
        ticketLong.setArriveTime(LocalTime.of(13,30));

        Ticket ticket2 = new Ticket();
        ticket2.setOriginCity("Florianopolis");
        ticket2.setDestinyCity("Sao Paulo");
        ticket2.setDepartureTime(LocalTime.of(10,30));
        ticket2.setArriveTime(LocalTime.of(7,30));

        Ticket ticket3 = new Ticket();
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
                thenReturn(Arrays.asList(ticketLong, ticket));

        Ticket shortest = calculatorService.calculateShortestWay("Criciuma", "Sao Paulo");
        assertThat(shortest).isEqualToComparingFieldByField(ticket);
        assertThat(shortest.getConnection()).isEqualToComparingFieldByField(ticket3);
    }

    @Test
    public void shouldReturnLongFly(){
        Ticket ticket = new Ticket();
        ticket.setOriginCity("Criciuma");
        ticket.setDestinyCity("Florianopolis");
        ticket.setDepartureTime(LocalTime.of(10,30));
        ticket.setArriveTime(LocalTime.of(7,30));

        Ticket ticket2 = new Ticket();
        ticket2.setOriginCity("Florianopolis");
        ticket2.setDestinyCity("Rio de Janeiro");
        ticket2.setDepartureTime(LocalTime.of(10,30));
        ticket2.setArriveTime(LocalTime.of(7,30));

        Ticket ticket3 = new Ticket();
        ticket3.setOriginCity("Rio de Janeiro");
        ticket3.setDestinyCity("Salvador");
        ticket3.setDepartureTime(LocalTime.of(7,0));
        ticket3.setArriveTime(LocalTime.of(18,0));


        when(ticketClient.
                searchByOriginAndDestinyCity("Criciuma", "Salvador")).
                thenReturn(Collections.emptyList());
        when(ticketClient.
                searchByOriginAndDestinyCity(null, "Salvador")).
                thenReturn(Arrays.asList(ticket3));
        when(ticketClient.
                searchByOriginAndDestinyCity(null, "Rio de Janeiro")).
                thenReturn(Arrays.asList(ticket2));
        when(ticketClient.
                searchByOriginAndDestinyCity("Criciuma", "Florianopolis")).
                thenReturn(Arrays.asList(ticket));

        Ticket shortest = calculatorService.calculateShortestWay("Criciuma", "Salvador");
        assertThat(shortest).isEqualTo(ticket);
        assertThat(shortest.getConnection()).isEqualTo(ticket2);
        assertThat(shortest.getConnection().getConnection()).isEqualTo(ticket3);
    }

    @Test
    public void shouldReturnLongFlyWithManyConnectionsBasedOnShortestTime(){
        Ticket ticket = new Ticket();
        ticket.setOriginCity("Criciuma");
        ticket.setDestinyCity("Florianopolis");
        ticket.setDepartureTime(LocalTime.of(7,30));
        ticket.setArriveTime(LocalTime.of(8,30));

        Ticket ticket1Long = new Ticket();
        ticket1Long.setOriginCity("Criciuma");
        ticket1Long.setDestinyCity("Florianopolis");
        ticket1Long.setDepartureTime(LocalTime.of(9,0));
        ticket1Long.setArriveTime(LocalTime.of(8,0));

        Ticket ticket2 = new Ticket();
        ticket2.setOriginCity("Florianopolis");
        ticket2.setDestinyCity("Rio de Janeiro");
        ticket2.setDepartureTime(LocalTime.of(10,30));
        ticket2.setArriveTime(LocalTime.of(11,30));

        Ticket ticket2Long = new Ticket();
        ticket2Long.setOriginCity("Florianopolis");
        ticket2Long.setDestinyCity("Rio de Janeiro");
        ticket2Long.setDepartureTime(LocalTime.of(19,30));
        ticket2Long.setArriveTime(LocalTime.of(20,30));

        Ticket ticket3 = new Ticket();
        ticket3.setOriginCity("Rio de Janeiro");
        ticket3.setDestinyCity("Salvador");
        ticket3.setDepartureTime(LocalTime.of(14,0));
        ticket3.setArriveTime(LocalTime.of(16,0));

        Ticket ticket3Long = new Ticket();
        ticket3Long.setOriginCity("Rio de Janeiro");
        ticket3Long.setDestinyCity("Salvador");
        ticket3Long.setDepartureTime(LocalTime.of(15,0));
        ticket3Long.setArriveTime(LocalTime.of(17,0));


        when(ticketClient.
                searchByOriginAndDestinyCity("Criciuma", "Salvador")).
                thenReturn(Collections.emptyList());
        when(ticketClient.
                searchByOriginAndDestinyCity(null, "Salvador")).
                thenReturn(Arrays.asList(ticket3, ticket3Long));
        when(ticketClient.
                searchByOriginAndDestinyCity(null, "Rio de Janeiro")).
                thenReturn(Arrays.asList(ticket2, ticket2Long));
        when(ticketClient.
                searchByOriginAndDestinyCity("Criciuma", "Florianopolis")).
                thenReturn(Arrays.asList(ticket, ticket1Long));

        Ticket shortest = calculatorService.calculateShortestWay("Criciuma", "Salvador");
        assertThat(shortest).isEqualTo(ticket);
        assertThat(shortest.getConnection()).isEqualTo(ticket2);
        assertThat(shortest.getConnection().getConnection()).isEqualTo(ticket3);
    }
}
