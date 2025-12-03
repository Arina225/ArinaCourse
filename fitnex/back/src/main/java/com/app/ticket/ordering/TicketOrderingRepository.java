package com.app.ticket.ordering;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketOrderingRepository extends JpaRepository<TicketOrdering, Long> {
}
