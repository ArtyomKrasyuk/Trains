package com.example.trains.controllers;

import com.example.trains.dto.*;
import com.example.trains.exceptions.LoginExistsException;
import com.example.trains.service.AuthService;
import com.example.trains.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class MainController {
    @Autowired
    private AuthService authService;
    @Autowired
    private ClientService clientService;

    @PostMapping("/registration")
    @ResponseBody
    public ResponseEntity<?> registration(@RequestBody @Valid ClientRegistrationDTO dto, HttpServletRequest request, HttpServletResponse response){
        try {
            authService.registration(dto, request, response);
            return ResponseEntity.ok().build();
        } catch (LoginExistsException | AuthenticationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody @Valid ClientLoginDTO dto, HttpServletRequest request, HttpServletResponse response){
        try {
            authService.login(dto, request, response);
            return ResponseEntity.ok().build();
        } catch (AuthenticationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    //------------------------------------------------------Personal account--------------------------------------------

    @PatchMapping("/client/change-firstname")
    public ResponseEntity<?> changeFirstname(@RequestBody @Valid FirstnameChangingDTO dto){
        try{
            clientService.changeFirstname(authService.getLogin(), dto.getFirstname());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PatchMapping("/client/change-lastname")
    public ResponseEntity<?> changeLastname(@RequestBody @Valid LastnameChangingDTO dto){
        try{
            clientService.changeLastname(authService.getLogin(), dto.getLastname());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PatchMapping("/client/change-patronymic")
    public ResponseEntity<?> changePatronymic(@RequestBody @Valid PatronymicChangingDTO dto){
        try{
            clientService.changePatronymic(authService.getLogin(), dto.getPatronymic());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PatchMapping("/client/change-login")
    public ResponseEntity<?> changeLogin(@RequestBody @Valid LoginChangingDTO dto){
        try{
            clientService.changeLogin(authService.getLogin(), dto.getLogin());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PatchMapping("/client/change-birthday")
    public ResponseEntity<?> changeBirthday(@RequestBody @Valid BirthdayChangingDTO dto){
        try{
            String birthday = dto.getBirthday();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate localDate = LocalDate.parse(birthday, formatter);
            Date date = Date.valueOf(localDate);
            clientService.changeBirthday(authService.getLogin(), date);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PatchMapping("/client/change-phone")
    public ResponseEntity<?> changePhone(@RequestBody @Valid PhoneChangingDTO dto){
        try{
            clientService.changePhone(authService.getLogin(), dto.getPhone());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PatchMapping("/client/change-gender")
    public ResponseEntity<?> changeGender(@RequestBody @Valid GenderChangingDTO dto){
        try{
            clientService.changeGender(authService.getLogin(), dto.getGender());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PatchMapping("/client/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordChangingDTO dto){
        try{
            clientService.changePassword(authService.getLogin(), dto.getPassword());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @GetMapping("/client/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }
}
