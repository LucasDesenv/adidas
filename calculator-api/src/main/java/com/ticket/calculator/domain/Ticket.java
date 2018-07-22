package com.ticket.calculator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Created by lusouza on 20/07/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@RedisHash("Ticket")
public class Ticket implements Serializable, Comparable<Ticket>{
    @Indexed
    private String originCity;
    @Indexed
    private String destinyCity;
    private LocalTime departureTime;
    private LocalTime arriveTime;
    @Id
    private String id;
    private Ticket connection;

    public Ticket() {
    }

    public Ticket(Ticket ticket) {
        this.originCity = ticket.getOriginCity();
        this.destinyCity = ticket.getDestinyCity();
        this.departureTime = ticket.getDepartureTime();
        this.arriveTime = ticket.getArriveTime();
        this.id = ticket.getId();
        if (ticket.getConnection() != null)
            this.connection = new Ticket(ticket.getConnection());
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

    public Ticket getConnection() {
        return connection;
    }

    public void setConnection(Ticket connection) {
        this.connection = connection;
    }

    public Ticket copy() {
        return new Ticket(this);
    }

    public boolean hasConnection(){
        return connection != null;
    }

    @Override
    public int compareTo(Ticket o2) {
        return this.getDepartureTime().compareTo(o2.getDepartureTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (!originCity.equals(ticket.originCity)) return false;
        if (!destinyCity.equals(ticket.destinyCity)) return false;
        if (!departureTime.equals(ticket.departureTime)) return false;
        return arriveTime.equals(ticket.arriveTime);
    }

    @Override
    public int hashCode() {
        int result = originCity.hashCode();
        result = 31 * result + destinyCity.hashCode();
        result = 31 * result + departureTime.hashCode();
        result = 31 * result + arriveTime.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ticket{");
        sb.append("originCity='").append(originCity).append('\'');
        sb.append(", destinyCity='").append(destinyCity).append('\'');
        sb.append(", departureTime=").append(departureTime);
        sb.append(", arriveTime=").append(arriveTime);
        sb.append(", id='").append(id).append('\'');
        sb.append(", connection=").append(connection);
        sb.append('}');
        return sb.toString();
    }
}
