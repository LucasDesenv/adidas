package com.travel.api.controller;

import com.travel.api.domain.TicketData;
import com.travel.api.dto.TicketDTO;
import com.travel.api.service.TicketService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Created by lusouza on 20/07/18.
 */
@Api
@RestController
@RequestMapping(value = "/v1/api/ticket", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketDTO> save(@RequestBody TicketDTO ticketDTO) throws Exception {
        final TicketData ticket = ticketService.save(ticketDTO);
        return ResponseEntity.created(new URI(String.format("/v1/api/ticket/%s", ticket.getId()))).body(new TicketDTO(ticket));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TicketDTO>> get(@RequestParam(name = "originCity", required = false) String originCity,
                                               @RequestParam(name = "destinyCity", required = false) String destinyCity) throws Exception {
//        List<TicketDTO> tickets = ticketService.searchByCity(Optional.ofNullable(originCity), Optional.ofNullable(destinyCity));
//        return ResponseEntity.created(new URI(String.format("/v1/api/ticket/%s", ticket.getId()))).body(new TicketDTO(ticket));
        return null;
    }
}
