package com.example.trains.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "carriage_type")
public class CarriageType {
    @Id
    @GeneratedValue
    @Column(name = "carriage_type_id")
    private int carriageTypeId;
    @Column(name = "type_name")
    private String typeName;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Carriage> carriages;
}
