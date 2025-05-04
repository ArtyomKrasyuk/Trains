package com.example.trains.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class BookingsDTO {
    private int tripId;
    @Size(min = 2, max = 20, message = "Имя должна содержать от 1 до 45 символов")
    @NotBlank(message = "Имя не может быть пустым")
    private String firstname;
    @Size(min = 1, max = 45, message = "Фамилия должно содержать от 2 до 20 символов")
    @NotBlank(message = "Фамилия не может быть пустой")
    private String lastname;
    @Size(max = 20, message = "Отчество должен быть не больше 20 символов")
    private String patronymic;
    @Email(message = "Некорректный email")
    @Size(min = 15, max = 254, message = "Email должен содержать от 15 до 254 символов")
    @NotBlank(message = "Email не может быть пустым")
    private String email;
    @Min(value = 1, message = "Пол указывается цифрой: 1 - мужской, 2 - женский")
    @Max(value = 2, message = "Пол указывается цифрой: 1 - мужской, 2 - женский")
    private short gender;
    @Size(min = 12, max = 12, message = "Номер телефона должен быть в формате +71234567890")
    @NotBlank(message = "Номер телефона не может быть пустым")
    @Pattern(regexp = "^\\+7\\d{10}$", message = "Номер телефона должен быть в формате +71234567890")
    private String phone;
    @NotBlank(message = "Дата рождения не может быть пустой")
    @Size(min = 10, max = 10, message = "Дата рождения должна иметь формат дд.мм.гггг")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.(19|20)\\d{2}$", message = "Дата рождения должна иметь формат дд.мм.гггг")
    private String birthday;
    @Pattern(regexp = "^\\d{10}$")
    private String passport;
    ArrayList<BookingDTO> bookings;
}
