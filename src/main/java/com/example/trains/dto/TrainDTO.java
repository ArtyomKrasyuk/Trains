package com.example.trains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class TrainDTO {
    private int trainId;
    private String trainName;
    ArrayList<CarriageDTO> carriages;
}
