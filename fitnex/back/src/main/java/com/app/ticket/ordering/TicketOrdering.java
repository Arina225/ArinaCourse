package com.app.ticket.ordering;

import com.app.appUser.AppUser;
import com.app.enums.OrderingStatus;
import com.app.ticket.Ticket;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TicketOrdering implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ticket_ordering_g")
    @SequenceGenerator(name = "ticket_ordering_g", sequenceName = "ticket_ordering_seq", allocationSize = 1)
    private Long id;

    private float price;

    @Enumerated(EnumType.STRING)
    private OrderingStatus status = OrderingStatus.WAITING;

    @ManyToOne
    private Ticket ticket;
    @ManyToOne
    private AppUser client;

    public TicketOrdering(Ticket ticket, AppUser client) {
        this.price = ticket.getPrice();
        this.ticket = ticket;
        this.client = client;
    }
}