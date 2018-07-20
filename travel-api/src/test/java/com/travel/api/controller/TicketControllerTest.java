package com.travel.api.controller;

import com.travel.api.dto.TicketDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by lusouza on 19/07/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
public class TicketControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void mustSaveATicket(){
        TicketDTO ticket = new TicketDTO();
        ticket.setOriginCity("Madrid");
        ticket.setDestinyCity("Barcelona");
        ticket.setDepartureTime(LocalTime.of(10,30));
        ticket.setArriveTime(LocalTime.of(7,30));

        final ResponseEntity<TicketDTO> ticketResponseEntity = restTemplate.postForEntity("/v1/api/ticket", ticket , TicketDTO.class);
        final TicketDTO body = ticketResponseEntity.getBody();

        assertThat(ticketResponseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(ticketResponseEntity.getHeaders().getLocation().getPath()).isEqualTo(String.format("/v1/api/ticket/%s", body.getId()));
        assertThat(body.getId()).isNotBlank();
        assertThat(body.getOriginCity()).isEqualTo(ticket.getOriginCity());
        assertThat(body.getDestinyCity()).isEqualTo(ticket.getDestinyCity());
        assertThat(body.getArriveTime()).isEqualTo(ticket.getArriveTime());
        assertThat(body.getDepartureTime()).isEqualTo(ticket.getDepartureTime());
    }

    @Test
    public void mustNotSaveATicketIfNoOriginCity(){
        TicketDTO ticket = new TicketDTO();
        ticket.setOriginCity(null);
        ticket.setDestinyCity("Barcelona");
        ticket.setDepartureTime(LocalTime.of(10,30));
        ticket.setArriveTime(LocalTime.of(7,30));

        final ResponseEntity<String> ticketResponseEntity = restTemplate.postForEntity("/v1/api/ticket", ticket, String.class);
        assertThat(ticketResponseEntity.getStatusCodeValue()).isEqualTo(400);
        assertThat(ticketResponseEntity.getBody()).contains("Origin city cannot be empty.");
    }

    @Test
    public void mustNotSaveATicketIfNoDestinyCity(){
        TicketDTO ticket = new TicketDTO();
        ticket.setOriginCity("Barcelona");
        ticket.setDestinyCity(null);
        ticket.setDepartureTime(LocalTime.of(10,30));
        ticket.setArriveTime(LocalTime.of(7,30));

        final ResponseEntity<String> ticketResponseEntity = restTemplate.postForEntity("/v1/api/ticket", ticket, String.class);
        assertThat(ticketResponseEntity.getStatusCodeValue()).isEqualTo(400);
        assertThat(ticketResponseEntity.getBody()).contains("Destiny city cannot be empty.");
    }

    @Test
    public void mustNotSaveATicketIfNoArriveTime(){
        TicketDTO ticket = new TicketDTO();
        ticket.setOriginCity("Barcelona");
        ticket.setDestinyCity("Madrid");
        ticket.setDepartureTime(LocalTime.of(10,30));
        ticket.setArriveTime(null);

        final ResponseEntity<String> ticketResponseEntity = restTemplate.postForEntity("/v1/api/ticket", ticket, String.class);
        assertThat(ticketResponseEntity.getStatusCodeValue()).isEqualTo(400);
        assertThat(ticketResponseEntity.getBody()).contains("Arrive time cannot be empty.");
    }

    @Test
    public void mustNotSaveATicketIfNoDepartureTime(){
        TicketDTO ticket = new TicketDTO();
        ticket.setOriginCity("Barcelona");
        ticket.setDestinyCity("Madrid");
        ticket.setDepartureTime(null);
        ticket.setArriveTime(LocalTime.of(7,30));

        final ResponseEntity<String> ticketResponseEntity = restTemplate.postForEntity("/v1/api/ticket", ticket, String.class);
        assertThat(ticketResponseEntity.getStatusCodeValue()).isEqualTo(400);
        assertThat(ticketResponseEntity.getBody()).contains("Departure time cannot be empty.");
    }
}