package com.example.trains.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class ClientRegistrationDTO {
    @Length(min = 2, max = 20, message = "Имя должна содержать от 1 до 45 символов")
    private String firstname;
    @Length(min = 1, max = 45, message = "Фамилия должно содержать от 2 до 20 символов")
    private String lastname;
    @Length(max = 20, message = "Отчество должен быть не больше 20 символов")
    private String patronymic;
    @Email(message = "Некорректный email")
    @Length(min = 15, max = 254, message = "Логин должен содержать от 15 до 254 символов")
    private String login;
    @Length(min = 8, max = 15, message = "Пароль должен содержать от 8 до 15 символов")
    private String password;
}
