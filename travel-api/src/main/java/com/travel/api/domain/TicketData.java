package com.travel.api.domain;

import java.time.LocalTime;

/**
 * Created by lusouza on 20/07/18.
 */
public interface TicketData {
    String getOriginCity();
    String getDestinyCity();
    LocalTime getDepartureTime();
    LocalTime getArriveTime();
    String getId();
}
