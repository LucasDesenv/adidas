package com.ticket.calculator.cache.repository;

import com.ticket.calculator.domain.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lusouza on 22/07/18.
 */
@Repository
public interface TicketCacheRepository extends CrudRepository<Ticket, String> {
    List<Ticket> findByOriginCityAndDestinyCity(@Param("originCity") String origin, @Param("destinyCity") String destinyCity);

    List<Ticket> findByDestinyCity(@Param("destinyCity") String destinyCity);

    List<Ticket> findByOriginCity(@Param("originCity") String origin);
}
