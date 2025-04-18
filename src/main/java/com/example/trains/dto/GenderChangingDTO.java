package com.example.trains.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class GenderChangingDTO {
    @Min(value = 1, message = "Пол указывается цифрой: 1 - мужской, 2 - женский")
    @Max(value = 2, message = "Пол указывается цифрой: 1 - мужской, 2 - женский")
    private short gender;
}
