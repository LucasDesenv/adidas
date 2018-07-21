package com.ticket.calculator.controller;

import com.ticket.calculator.client.TicketClient;
import com.ticket.calculator.dto.TicketDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lusouza on 20/07/18.
 */
@Api
@RestController
@RequestMapping(value = "/v1/api/calculator", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class CalculatorController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    private String url = "http://localhost:8080/travel";
    @Autowired
    private TicketClient ticketClient;


    @GetMapping("/ping")
    public String teste(){
        return ticketClient.ping();
    }

    @GetMapping
    public List<TicketDTO> calculateTheShortesttWay(@RequestParam(name = "fromCity") String fromCity,
                                                           @RequestParam(name = "toCity") String toCity){

        //Criciuma - Sao Paulo
        List<TicketDTO> nonStoppingTicket = getNonStoppingTicket(fromCity, toCity);
        if (!nonStoppingTicket.isEmpty()){
            return nonStoppingTicket;
        }

        final List<TicketDTO> connections = new ArrayList<>();

        //Só Sao Paulo
        final ResponseEntity<List<TicketDTO>> response2 = restTemplate.exchange(String.format("%s/v1/api/ticket/search?destinyCity=%s", url, toCity),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<TicketDTO>>() {
                });
        TicketDTO destiny = response2.getBody().get(0);
        int maxConnections = 2;

        //Criciúma até Origem acima
        ResponseEntity<List<TicketDTO>> response3 = restTemplate.exchange(String.format("%s/v1/api/ticket/search?originCity=%s&destinyCity=%s", url,fromCity, destiny.getOriginCity()),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<TicketDTO>>() {
                });
        List<TicketDTO> fromCityToConnection = response3.getBody();
        if (!fromCityToConnection.isEmpty()){
            connections.add(fromCityToConnection.get(0));
            connections.add(destiny);
            return connections;
        }



        ResponseEntity<List<TicketDTO>> response4 = restTemplate.exchange(String.format("%s/v1/api/ticket/search?destinyCity=%s", url, destiny.getOriginCity()),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<TicketDTO>>() {
                });
        TicketDTO secondConnection = response4.getBody().get(0);

        ResponseEntity<List<TicketDTO>> response5 = restTemplate.exchange(String.format("%s/v1/api/ticket/search?originCity=%s&destinyCity=%s", url, fromCity, secondConnection.getOriginCity()),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<TicketDTO>>() {
                });
        TicketDTO firstConnection = response5.getBody().get(0);

        connections.add(firstConnection);
        connections.add(secondConnection);
        connections.add(destiny);

        return connections;
    }

    private List<TicketDTO> getNonStoppingTicket(@RequestParam(name = "fromCity") String fromCity, @RequestParam(name = "toCity") String toCity) {
        ResponseEntity<List<TicketDTO>> response = restTemplate.exchange(String.format("%s/v1/api/ticket/search?originCity=%s&destinyCity=%s", url, fromCity, toCity),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<TicketDTO>>() {
        });
        return response.getBody();
    }
}
