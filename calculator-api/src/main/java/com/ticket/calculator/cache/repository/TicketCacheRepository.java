package com.ticket.calculator.cache.repository;

import com.ticket.calculator.dto.TicketDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lusouza on 22/07/18.
 */
@Repository
public interface TicketCacheRepository extends CrudRepository<TicketDTO, String> {
    List<TicketDTO> findByOriginCityAndDestinyCity(@Param("originCity") String origin, @Param("destinyCity") String destinyCity);

    List<TicketDTO> findByDestinyCity(@Param("destinyCity") String destinyCity);

    List<TicketDTO> findByOriginCity(@Param("originCity") String origin);
}
