package com.travel.api.repository;

import com.travel.api.domain.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by lusouza on 20/07/18.
 */
public interface TicketRepository extends MongoRepository<Ticket, String> {
}
