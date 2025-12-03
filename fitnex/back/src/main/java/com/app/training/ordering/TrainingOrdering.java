package com.app.training.ordering;

import com.app.appUser.AppUser;
import com.app.enums.OrderingStatus;
import com.app.training.Training;
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
public class TrainingOrdering implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "training_ordering_g")
    @SequenceGenerator(name = "training_ordering_g", sequenceName = "training_ordering_seq", allocationSize = 1)
    private Long id;

    private float price;

    @Enumerated(EnumType.STRING)
    private OrderingStatus status = OrderingStatus.WAITING;

    @ManyToOne
    private Training training;
    @ManyToOne
    private AppUser client;

    public TrainingOrdering(Training training, AppUser client) {
        this.price = training.getPrice();
        this.training = training;
        this.client = client;
    }

}
