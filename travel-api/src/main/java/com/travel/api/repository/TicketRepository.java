package com.travel.api.repository;

import com.travel.api.domain.Ticket;
import com.travel.api.domain.TicketData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by lusouza on 20/07/18.
 */
public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByOriginCityAndDestinyCity(String originCity, String destinyCity);

    List<TicketData> findByDestinyCity(String destinyCity);

    List<TicketData> findByOriginCity(String originCity);
}
