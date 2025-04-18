package com.example.trains.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientRegistrationDTO {
    @Size(min = 2, max = 20, message = "Имя должна содержать от 1 до 45 символов")
    @NotBlank(message = "Имя не может быть пустым")
    private String firstname;
    @Size(min = 1, max = 45, message = "Фамилия должно содержать от 2 до 20 символов")
    @NotBlank(message = "Фамилия не может быть пустой")
    private String lastname;
    @Size(max = 20, message = "Отчество должен быть не больше 20 символов")
    private String patronymic;
    @Email(message = "Некорректный email")
    @Size(min = 15, max = 254, message = "Логин должен содержать от 15 до 254 символов")
    @NotBlank(message = "Email не может быть пустым")
    private String login;
    @Size(min = 8, max = 15, message = "Пароль должен содержать от 8 до 15 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
