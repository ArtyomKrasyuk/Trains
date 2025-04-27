package com.example.trains.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

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
    @Column(name = "passport_series_and_number")
    private String passportSeriesAndNumber;
    @Column(name = "passport_issue_date")
    private Date passportIssueDate;
    @Column(name = "who_issued")
    private String whoIssued;
    private double price;

    public Booking(
            Client client,
            Place place,
            Trip trip,
            String passportSeriesAndNumber,
            Date passportIssueDate,
            String whoIssued,
            double price
    ){
        this.client = client;
        this.place = place;
        this.trip = trip;
        this.passportSeriesAndNumber = passportSeriesAndNumber;
        this.passportIssueDate = passportIssueDate;
        this.whoIssued = whoIssued;
        this.price = price;
    }
}
