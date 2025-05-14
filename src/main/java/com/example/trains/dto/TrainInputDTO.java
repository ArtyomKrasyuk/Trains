package com.example.trains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class TrainInputDTO {
    private int trainId;
    private String trainName;
    ArrayList<CarriageInputDTO> carriages;
}
