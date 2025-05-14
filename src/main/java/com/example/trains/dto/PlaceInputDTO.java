package com.example.trains.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceInputDTO {
    @Min(1)
    @Max(96)
    private short position;
    private double comfortFactor;
    @Min(0)
    @Max(2)
    private short gender;
}
