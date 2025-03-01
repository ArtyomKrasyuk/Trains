package com.example.trains.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientLoginDTO {
    private String login;
    private String password;
}
