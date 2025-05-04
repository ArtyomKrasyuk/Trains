package com.example.trains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class BookingDTO {
    private short carriageNumber;
    private short position;
    private double price;
}
