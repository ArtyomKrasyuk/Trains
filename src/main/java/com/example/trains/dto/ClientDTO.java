package com.example.trains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientDTO {
    private String firstname;
    private String lastname;
    private String patronymic;
    private String login;
    private short gender;
    private String phone;
    private String birthday;
}
