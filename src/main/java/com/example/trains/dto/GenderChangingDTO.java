package com.example.trains.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class GenderChangingDTO {
    @Min(value = 1, message = "Неправильный ввод пола")
    @Max(value = 2, message = "Неправильный ввод пола")
    private short value;
}
