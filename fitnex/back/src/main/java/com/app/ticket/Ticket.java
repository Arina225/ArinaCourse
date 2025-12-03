package com.app.ticket;

import com.app.enums.OrderingStatus;
import com.app.ticket.ordering.TicketOrdering;
import com.app.util.Global;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ticket implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ticket_g")
    @SequenceGenerator(name = "ticket_g", sequenceName = "ticket_seq", allocationSize = 1)
    private Long id;

    private String name;
    private float price;

    @Column(length = 1000)
    private String img = "";
    @Column(length = 1000)
    private String file = "";

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<TicketOrdering> orderings = new ArrayList<>();

    public Ticket(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public void update(Ticket update) {
        this.name = update.getName();
        this.price = update.getPrice();
    }

    public float getIncome() {
        return Global.round(orderings.stream().reduce(0f, (i, ordering) -> {
            if (ordering.getStatus() == OrderingStatus.CONFIRMED) return i + ordering.getPrice();
            return i;
        }, Float::sum));
    }

}