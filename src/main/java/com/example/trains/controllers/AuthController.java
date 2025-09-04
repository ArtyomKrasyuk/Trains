package com.example.trains.controllers;

import com.example.trains.dto.ClientLoginDTO;
import com.example.trains.dto.ClientRegistrationDTO;
import com.example.trains.exceptions.NotAdminException;
import com.example.trains.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    // Метод обработки регистрации
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid ClientRegistrationDTO dto, HttpServletRequest request, HttpServletResponse response){
        try {
            authService.registration(dto, request, response);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(e.getMessage());
        }
    }

    // Метод обработки авторизации клиента
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid ClientLoginDTO dto, HttpServletRequest request, HttpServletResponse response){
        try {
            authService.login(dto, request, response);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(e.getMessage());
        }
    }

    // Метод обработки авторизации администратора
    @PostMapping("/login-admin")
    public ResponseEntity<?> loginAdmin(@RequestBody @Valid ClientLoginDTO dto, HttpServletRequest request, HttpServletResponse response){
        try {
            authService.loginAdmin(dto, request, response);
            return ResponseEntity.ok().build();
        }
        catch (NotAdminException e){
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(e.getMessage());
        }
    }
}
