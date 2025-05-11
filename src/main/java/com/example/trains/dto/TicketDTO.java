package com.example.trains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketDTO {
    private int trainId;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String birthday;
    private String passport;
    private String ticketNumber;
    private short carriageNumber;
    private short position;
    private String type;
}
