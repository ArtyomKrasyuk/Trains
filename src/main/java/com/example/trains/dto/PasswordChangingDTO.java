package com.example.trains.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordChangingDTO {
    @Size(min = 8, max = 15, message = "Пароль должен содержать от 8 до 15 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
