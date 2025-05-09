package com.example.trains.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BirthdayChangingDTO {
    @NotBlank(message = "Дата рождения не может быть пустой")
    @Size(min = 10, max = 10, message = "Дата рождения должна иметь формат дд.мм.гггг")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.(19|20)\\d{2}$", message = "Дата рождения должна иметь формат дд.мм.гггг")
    private String value;
}
