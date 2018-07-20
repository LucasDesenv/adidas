package com.travel.api.dto;

import com.travel.api.domain.TicketData;

import java.time.LocalTime;

/**
 * Created by lusouza on 20/07/18.
 */
public class TicketDTO implements TicketData {
    private String originCity;
    private String destinyCity;
    private LocalTime departureTime;
    private LocalTime arriveTime;
    private String id;

    public TicketDTO() {
    }

    public TicketDTO(TicketData ticketData) {
        this.originCity = ticketData.getOriginCity();
        this.destinyCity = ticketData.getDestinyCity();
        this.departureTime = ticketData.getDepartureTime();
        this.arriveTime = ticketData.getArriveTime();
        this.id = ticketData.getId();
    }

    @Override
    public String getOriginCity() {
        return originCity;
    }

    @Override
    public String getDestinyCity() {
        return destinyCity;
    }

    @Override
    public LocalTime getDepartureTime() {
        return departureTime;
    }

    @Override
    public LocalTime getArriveTime() {
        return arriveTime;
    }

    @Override
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
