package com.example.trains.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class ClientLoginDTO {
    @Email(message = "Некорректный email")
    @Length(min = 15, max = 254, message = "Логин должен содержать от 15 до 254 символов")
    private String login;
    @Length(min = 8, max = 15, message = "Пароль должен содержать от 8 до 15 символов")
    private String password;
}
