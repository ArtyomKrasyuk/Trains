package com.example.trains.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "train")
public class Train {
    @Id
    @Column(name = "train_id")
    private int trainId;
    @Column(name = "train_name")
    private String trainName;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Carriage> carriages;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Trip> trips;

    public Train(int trainId, String trainName){
        this.trainId = trainId;
        this.trainName = trainName;
    }
}
