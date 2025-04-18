package com.example.trains.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PhoneChangingDTO {
    @Size(min = 12, max = 12, message = "Номер телефона должен быть в формате +71234567890")
    @NotBlank(message = "Номер телефона не может быть пустым")
    @Pattern(regexp = "^\\+7\\d{10}$", message = "Номер телефона должен быть в формате +71234567890")
    private String phone;
}
