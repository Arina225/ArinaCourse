package com.app.training;

import com.app.appUser.AppUser;
import com.app.enums.OrderingStatus;
import com.app.training.ordering.TrainingOrdering;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Training implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "training_g")
    @SequenceGenerator(name = "training_g", sequenceName = "training_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String date;
    private int duration;
    private int places;
    private float price;

    @ManyToOne
    private AppUser trainer;
    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<TrainingOrdering> orderings = new ArrayList<>();

    public Training(String name, String date, int duration, int places, float price) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.places = places;
        this.price = price;
    }

    public void update(Training update) {
        this.name = update.getName();
        this.date = update.getDate();
        this.duration = update.getDuration();
        this.places = update.getPlaces();
        this.price = update.getPrice();
    }

    public int getFree() {
        return Math.max(places - orderings.stream().reduce(0, (i, ordering) -> {
            if (ordering.getStatus() == OrderingStatus.CONFIRMED) return i + 1;
            return i;
        }, Integer::sum), 0);
    }

    public List<Long> getOrderingsClientsId() {
        return orderings.stream().map(i -> i.getClient().getId()).collect(Collectors.toList());
    }

}