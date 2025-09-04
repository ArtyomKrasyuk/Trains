package com.example.trains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TripInputDTO {
    private int trainId;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private boolean hasFreePlaces;
}
