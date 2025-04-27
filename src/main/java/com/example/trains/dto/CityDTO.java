package com.example.trains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CityDTO {
    private String cityName;
    private double rangeFactor;
}
