package com.app.ticket.ordering;

import com.app.appUser.UserService;
import com.app.enums.OrderingStatus;
import com.app.system.exception.ObjectNotFoundException;
import com.app.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketOrderingService {

    private final TicketOrderingRepository repository;
    private final UserService userService;
    private final TicketService ticketService;

    public List<TicketOrdering> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<TicketOrdering> findAllMy() {
        List<TicketOrdering> orderings = userService.getCurrentUser().getTicketOrderings();
        orderings.sort(Comparator.comparing(TicketOrdering::getId));
        Collections.reverse(orderings);
        return orderings;
    }

    public TicketOrdering find(String id) {
        return repository.findById(Long.parseLong(id)).orElseThrow(() -> new ObjectNotFoundException("Не найдена заявка по ИД: " + id));
    }

    public TicketOrdering save(String ticketId) {
        return repository.save(new TicketOrdering(ticketService.find(ticketId), userService.getCurrentUser()));
    }

    public TicketOrdering confirmed(String id) {
        TicketOrdering ordering = find(id);
        ordering.setStatus(OrderingStatus.CONFIRMED);
        return repository.save(ordering);
    }

    public TicketOrdering rejected(String id) {
        TicketOrdering ordering = find(id);
        ordering.setStatus(OrderingStatus.REJECTED);
        return repository.save(ordering);
    }

}
