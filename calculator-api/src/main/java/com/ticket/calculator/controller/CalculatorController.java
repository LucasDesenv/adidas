package com.ticket.calculator.controller;

import com.ticket.calculator.client.TicketClient;
import com.ticket.calculator.dto.TicketDTO;
import com.ticket.calculator.service.CalculatorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lusouza on 20/07/18.
 */
@Api
@RestController
@RequestMapping(value = "/v1/api/calculator", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class CalculatorController {

    //Feign will resolve it in runtime.
    @Autowired
    private TicketClient ticketClient;
    @Autowired
    private CalculatorService calculatorService;


    @GetMapping("/check-integration")
    public String pingToTravelApi(){
        return ticketClient.ping();
    }

    @GetMapping("/shortest")
    public TicketDTO calculateTheShortestWay(@RequestParam(name = "fromCity") String fromCity,
                                                           @RequestParam(name = "toCity") String toCity){
        return calculatorService.calculateShortestWay(fromCity,toCity);
    }
}
