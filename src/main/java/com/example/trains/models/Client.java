package com.example.trains.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client {
    public enum Role{
        CLIENT,
        ADMIN
    }

    @Id
    @GeneratedValue
    @Column(name = "client_id")
    private int clientId;
    private String firstname;
    private String lastname;
    private String patronymic;
    private short gender;
    private String phone;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Booking> bookings;
}
