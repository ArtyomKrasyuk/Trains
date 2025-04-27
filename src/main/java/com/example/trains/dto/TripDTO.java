package com.example.trains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TripDTO {
    private int tripId;
    private int trainId;
    private String destination;
    private String departureTime;
    private String arrivalTime;
}
