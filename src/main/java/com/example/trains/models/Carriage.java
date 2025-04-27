package com.example.trains.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "carriage")
public class Carriage {
    @Id
    @GeneratedValue
    @Column(name = "carriage_id")
    private int carriageId;
    @Column(name = "number_of_seats")
    private int numberOfSeats;
    @Column(name = "carriage_number")
    private short carriageNumber;
    @Column(name = "top_block_width")
    private short topBlockWidth;
    @Column(name = "bottom_block_width")
    private short bottomBlockWidth;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", referencedColumnName = "train_id")
    private Train train;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", referencedColumnName = "carriage_type_id")
    private CarriageType type;

    @OneToMany(mappedBy = "carriage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Place> places;

    public Carriage(int numberOfSeats, short carriageNumber, short topBlockWidth, short bottomBlockWidth, Train train, CarriageType type){
        this.numberOfSeats = numberOfSeats;
        this.carriageNumber = carriageNumber;
        this.topBlockWidth = topBlockWidth;
        this.bottomBlockWidth = bottomBlockWidth;
        this.train = train;
        this.type = type;
    }
}
