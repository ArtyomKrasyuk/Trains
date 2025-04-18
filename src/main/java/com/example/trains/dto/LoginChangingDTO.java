package com.example.trains.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginChangingDTO {
    @Email(message = "Некорректный email")
    @Size(min = 15, max = 254, message = "Логин должен содержать от 15 до 254 символов")
    @NotBlank(message = "Email не может быть пустым")
    private String login;
}
