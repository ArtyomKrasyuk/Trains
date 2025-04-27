package com.example.trains.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "place")
public class Place {
    @Id
    @GeneratedValue
    @Column(name = "place_id")
    private int placeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carriage_id", referencedColumnName = "carriage_id")
    private Carriage carriage;
    @Column(name = "passenger_gender")
    private short passengerGender;
    @Column(name = "comfort_factor")
    private double comfortFactor;
    private short position;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Booking> bookings;

    public Place(Carriage carriage, short passengerGender, double comfortFactor, short position){
        this.carriage = carriage;
        this.passengerGender = passengerGender;
        this.comfortFactor = comfortFactor;
        this.position = position;
    }
}
