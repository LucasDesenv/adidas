package com.ticket.calculator.dto;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Created by lusouza on 20/07/18.
 */
public class TicketDTO implements Serializable{
    private String originCity;
    private String destinyCity;
    private LocalTime departureTime;
    private LocalTime arriveTime;
    private String id;

    public TicketDTO() {
    }

    public String getOriginCity() {
        return originCity;
    }

    public String getDestinyCity() {
        return destinyCity;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public LocalTime getArriveTime() {
        return arriveTime;
    }

    public String getId() {
        return id;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public void setDestinyCity(String destinyCity) {
        this.destinyCity = destinyCity;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setArriveTime(LocalTime arriveTime) {
        this.arriveTime = arriveTime;
    }
}
