package com.example.trains.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "passenger")
public class Passenger {
    @Id
    @GeneratedValue
    @Column(name = "passenger_id")
    private int passengerId;
    private String firstname;
    private String lastname;
    private String patronymic;
    private Date birthday;
    private short gender;
    private String phone;
    private String email;
    @Column(name = "passport_series_and_number")
    private String passportSeriesAndNumber;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Booking> bookings;

    public Passenger(
            String firstname,
            String lastname,
            String patronymic,
            Date birthday,
            short gender,
            String phone,
            String email,
            String passportSeriesAndNumber
    ){
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.passportSeriesAndNumber = passportSeriesAndNumber;
    }
}
