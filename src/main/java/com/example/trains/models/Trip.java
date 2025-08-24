package com.example.trains.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private int tripId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", referencedColumnName = "train_id")
    private Train train;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination", referencedColumnName = "city_id")
    private City destination;
    @Column(name = "departure_time")
    private Timestamp departureTime;
    @Column(name = "arrival_time")
    private Timestamp arrivalTime;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Booking> bookings;

    public Trip(Train train, City destination, Timestamp departureTime, Timestamp arrivalTime){
        this.train = train;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }
}
