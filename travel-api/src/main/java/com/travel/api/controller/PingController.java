package com.travel.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lusouza on 19/07/18.
 */
@RestController
public class PingController {

    @GetMapping("/ping")
    public String ping()
    {
        return "pong";
    }
}
