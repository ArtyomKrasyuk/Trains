package com.example.trains.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue
    @Column(name = "booking_id")
    private int bookingId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", referencedColumnName = "place_id")
    private Place place;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", referencedColumnName = "trip_id")
    private Trip trip;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id", referencedColumnName = "passenger_id")
    private Passenger passenger;
    private double price;
    @Column(name = "ticket_number")
    private String ticketNumber;

    public Booking(
            Client client,
            Place place,
            Trip trip,
            Passenger passenger,
            double price
    ){
        this.client = client;
        this.place = place;
        this.trip = trip;
        this.passenger = passenger;
        this.price = price;

        StringBuilder sb = new StringBuilder();
        sb.append(trip.getDepartureTime().getTime())
                .append(trip.getTrain().getTrainId())
                .append(place.getCarriage().getCarriageNumber())
                .append(place.getPosition());
        this.ticketNumber = sb.toString();
    }
}
