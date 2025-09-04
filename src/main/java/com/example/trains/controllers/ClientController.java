package com.example.trains.controllers;

import com.example.trains.dto.*;
import com.example.trains.service.AuthService;
import com.example.trains.service.ClientService;
import com.example.trains.service.DatabaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RestController
public class ClientController {
    @Autowired
    private AuthService authService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private DatabaseService databaseService;

    // Метод обработки изменения имени
    @PatchMapping("/client/firstname")
    public ResponseEntity<?> changeFirstname(@RequestBody @Valid FirstnameChangingDTO dto){
        try{
            clientService.changeFirstname(authService.getLogin(), dto.getValue());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки изменения фамилии
    @PatchMapping("/client/lastname")
    public ResponseEntity<?> changeLastname(@RequestBody @Valid LastnameChangingDTO dto){
        try{
            clientService.changeLastname(authService.getLogin(), dto.getValue());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки изменения отчества
    @PatchMapping("/client/patronymic")
    public ResponseEntity<?> changePatronymic(@RequestBody @Valid PatronymicChangingDTO dto){
        try{
            clientService.changePatronymic(authService.getLogin(), dto.getValue());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки изменения электронной почты
    @PatchMapping("/client/login")
    public ResponseEntity<?> changeLogin(@RequestBody @Valid LoginChangingDTO dto){
        try{
            clientService.changeLogin(authService.getLogin(), dto.getValue());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки изменения даты рождения
    @PatchMapping("/client/birthday")
    public ResponseEntity<?> changeBirthday(@RequestBody @Valid BirthdayChangingDTO dto){
        try{
            String birthday = dto.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate localDate = LocalDate.parse(birthday, formatter);
            Date date = Date.valueOf(localDate);
            clientService.changeBirthday(authService.getLogin(), date);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки изменения номера телефона
    @PatchMapping("/client/phone")
    public ResponseEntity<?> changePhone(@RequestBody @Valid PhoneChangingDTO dto){
        try{
            clientService.changePhone(authService.getLogin(), dto.getValue());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки изменения пола
    @PatchMapping("/client/gender")
    public ResponseEntity<?> changeGender(@RequestBody @Valid GenderChangingDTO dto){
        try{
            clientService.changeGender(authService.getLogin(), dto.getValue());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки изменения пароля
    @PatchMapping("/client/password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordChangingDTO dto){
        try{
            clientService.changePassword(authService.getLogin(), dto.getOldPassword(), dto.getNewPassword());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки запроса данных пользователя
    @GetMapping("/client/data")
    public ResponseEntity<?> getClientData(){
        try{
            ClientDTO dto = clientService.getClientData(authService.getLogin());
            return ResponseEntity.ok(dto);
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки запроса удаления билета
    @DeleteMapping("/client/ticket/{ticketNumber}")
    public ResponseEntity<?> deleteTicket(@PathVariable String ticketNumber){
        try{
            databaseService.deleteBooking(ticketNumber);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки запроса списка билетов
    @GetMapping("/client/tickets")
    public ResponseEntity<?> getTickets(){
        try{
            ArrayList<TicketDTO> tickets = databaseService.getTickets(authService.getLogin());
            return ResponseEntity.ok(tickets);
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки запроса бронирования
    @PostMapping("/client/book")
    public ResponseEntity<?> book(@RequestBody BookingsDTO dto){
        try{
            boolean result = databaseService.book(dto, authService.getLogin());
            return ResponseEntity.ok(result);
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки запроса проверки авторизации клиента
    @GetMapping("/client/test")
    public ResponseEntity<?> testClient(){
        return ResponseEntity.ok().build();
    }
}
