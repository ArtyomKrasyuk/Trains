package com.example.trains.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class CarriageDTO {
    @Min(1)
    @Max(20)
    private short number;
    private String type;
    @Min(12)
    @Max(96)
    private int numberOfSeats;
    @Min(1)
    @Max(3)
    private short topBlockWidth;
    @Min(1)
    @Max(3)
    private short bottomBlockWidth;
    ArrayList<PlaceDTO> places;
}
