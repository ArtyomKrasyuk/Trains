package com.example.trains.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatronymicChangingDTO {
    @Size(max = 20, message = "Отчество должен быть не больше 20 символов")
    private String value;
}
