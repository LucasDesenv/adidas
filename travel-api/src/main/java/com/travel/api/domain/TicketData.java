package com.travel.api.domain;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Created by lusouza on 20/07/18.
 */
public interface TicketData extends Serializable{
    String getOriginCity();
    String getDestinyCity();
    LocalTime getDepartureTime();
    LocalTime getArriveTime();
    String getId();
}
