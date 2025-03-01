package com.example.trains.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientRegistrationDTO {
    private String firstname;
    private String lastname;
    private String patronymic;
    private short gender;
    private String phone;
    private String login;
    private String password;
}
