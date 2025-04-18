package com.example.trains.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientLoginDTO {
    @Email(message = "Некорректный email")
    @Size(min = 15, max = 254, message = "Логин должен содержать от 15 до 254 символов")
    @NotBlank(message = "Email не может быть пустым")
    private String login;
    @Size(min = 8, max = 15, message = "Пароль должен содержать от 8 до 15 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
