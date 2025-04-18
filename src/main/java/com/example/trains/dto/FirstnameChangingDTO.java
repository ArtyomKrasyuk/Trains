package com.example.trains.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FirstnameChangingDTO {
    @Size(min = 2, max = 20, message = "Имя должна содержать от 1 до 45 символов")
    @NotBlank(message = "Имя не может быть пустым")
    private String firstname;
}
