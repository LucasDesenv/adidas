package com.ticket.calculator.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Created by lusouza on 20/07/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketDTO implements Serializable{
    private String originCity;
    private String destinyCity;
    private LocalTime departureTime;
    private LocalTime arriveTime;
    private String id;
    private TicketDTO connection;

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

    public TicketDTO getConnection() {
        return connection;
    }

    public void setConnection(TicketDTO connection) {
        this.connection = connection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketDTO ticketDTO = (TicketDTO) o;

        if (!originCity.equals(ticketDTO.originCity)) return false;
        if (!destinyCity.equals(ticketDTO.destinyCity)) return false;
        if (!departureTime.equals(ticketDTO.departureTime)) return false;
        return arriveTime.equals(ticketDTO.arriveTime);
    }

    @Override
    public int hashCode() {
        int result = originCity.hashCode();
        result = 31 * result + destinyCity.hashCode();
        result = 31 * result + departureTime.hashCode();
        result = 31 * result + arriveTime.hashCode();
        return result;
    }
}
