package com.example.trains.controllers;

import com.example.trains.dto.ClientLoginDTO;
import com.example.trains.dto.ClientRegistrationDTO;
import com.example.trains.exceptions.LoginExistsException;
import com.example.trains.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    @ResponseBody
    public String registration(@RequestBody ClientRegistrationDTO dto, HttpServletRequest request, HttpServletResponse response){
        try {
            Authentication authentication = authService.registration(dto, request, response);
            return authentication.getName();
        } catch (LoginExistsException | AuthenticationException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody ClientLoginDTO dto, HttpServletRequest request, HttpServletResponse response){
        try {
            Authentication authentication = authService.login(dto, request, response);
            return authentication.getName();
        } catch (AuthenticationException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/api/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }
}
