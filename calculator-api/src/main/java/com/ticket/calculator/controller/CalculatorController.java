package com.ticket.calculator.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by lusouza on 20/07/18.
 */
@Api
@RestController
@RequestMapping(value = "/v1/api/calculator", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class CalculatorController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String teste(){
        return restTemplate.getForObject("http://localhost:8080/travel/ping", String.class);
    }
}
