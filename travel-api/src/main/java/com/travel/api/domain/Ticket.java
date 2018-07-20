package com.travel.api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.Optional;

/**
 * Created by lusouza on 19/07/18.
 */
@Document(collection = "ticket")
public class Ticket implements TicketData {
    private String originCity;
    private String destinyCity;
    private LocalTime departureTime;
    private LocalTime arriveTime;
    @Id
    private String id;

    public Ticket() {
    }

    public Ticket(TicketData ticketData) {
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

    public void prepareToSave() {
        Optional.ofNullable(originCity).orElseThrow(() -> new IllegalArgumentException("Origin city cannot be empty."));
        Optional.ofNullable(destinyCity).orElseThrow(() -> new IllegalArgumentException("Destiny city cannot be empty."));
        Optional.ofNullable(arriveTime).orElseThrow(() -> new IllegalArgumentException("Arrive time cannot be empty."));
        Optional.ofNullable(departureTime).orElseThrow(() -> new IllegalArgumentException("Departure time cannot be empty."));
        this.id = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        return id != null ? id.equals(ticket.id) : ticket.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "originCity='" + originCity + '\'' +
                ", destinyCity='" + destinyCity + '\'' +
                ", departureTime=" + departureTime +
                ", arriveTime=" + arriveTime +
                ", id=" + id +
                '}';
    }

}
