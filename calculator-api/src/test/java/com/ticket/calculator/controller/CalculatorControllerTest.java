package com.ticket.calculator.controller;

import com.ticket.calculator.dto.TicketDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by lusouza on 20/07/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
public class CalculatorControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void mustSaveATicket(){
        Map<Object, Object> uriVariables = new HashMap<>();
        uriVariables.put("fromCity", "Barcelona");
        uriVariables.put("toCity", "Madrid");

        final ResponseEntity<TicketDTO> ticketResponseEntity = restTemplate.getForEntity("/v1/api/calculator", TicketDTO.class, uriVariables);
        assertThat(ticketResponseEntity.getStatusCodeValue()).isEqualTo(200);
    }
}
