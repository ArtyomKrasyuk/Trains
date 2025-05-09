package com.example.trains.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LastnameChangingDTO {
    @Size(min = 1, max = 45, message = "Фамилия должно содержать от 2 до 20 символов")
    @NotBlank(message = "Фамилия не может быть пустой")
    private String value;
}
