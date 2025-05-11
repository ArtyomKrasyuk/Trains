package com.example.trains.controllers;

import com.example.trains.dto.*;
import com.example.trains.exceptions.LoginExistsException;
import com.example.trains.service.AuthService;
import com.example.trains.service.ClientService;
import com.example.trains.service.DatabaseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RestController
public class MainController {
    @Autowired
    private AuthService authService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private DatabaseService databaseService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid ClientRegistrationDTO dto, HttpServletRequest request, HttpServletResponse response){
        try {
            authService.registration(dto, request, response);
            return ResponseEntity.ok().build();
        } catch (LoginExistsException | AuthenticationException e) {
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid ClientLoginDTO dto, HttpServletRequest request, HttpServletResponse response){
        try {
            authService.login(dto, request, response);
            return ResponseEntity.ok().build();
        } catch (AuthenticationException e) {
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(e.getMessage());
        }
    }

    //------------------------------------------------------Personal account--------------------------------------------

    @PatchMapping("/client/change-firstname")
    public ResponseEntity<?> changeFirstname(@RequestBody @Valid FirstnameChangingDTO dto){
        try{
            clientService.changeFirstname(authService.getLogin(), dto.getValue());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    @PatchMapping("/client/change-lastname")
    public ResponseEntity<?> changeLastname(@RequestBody @Valid LastnameChangingDTO dto){
        try{
            clientService.changeLastname(authService.getLogin(), dto.getValue());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    @PatchMapping("/client/change-patronymic")
    public ResponseEntity<?> changePatronymic(@RequestBody @Valid PatronymicChangingDTO dto){
        try{
            clientService.changePatronymic(authService.getLogin(), dto.getValue());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    @PatchMapping("/client/change-login")
    public ResponseEntity<?> changeLogin(@RequestBody @Valid LoginChangingDTO dto){
        try{
            clientService.changeLogin(authService.getLogin(), dto.getValue());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    @PatchMapping("/client/change-birthday")
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

    @PatchMapping("/client/change-phone")
    public ResponseEntity<?> changePhone(@RequestBody @Valid PhoneChangingDTO dto){
        try{
            clientService.changePhone(authService.getLogin(), dto.getValue());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    @PatchMapping("/client/change-gender")
    public ResponseEntity<?> changeGender(@RequestBody @Valid GenderChangingDTO dto){
        try{
            clientService.changeGender(authService.getLogin(), dto.getValue());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    @PatchMapping("/client/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordChangingDTO dto){
        try{
            clientService.changePassword(authService.getLogin(), dto.getOldPassword(), dto.getNewPassword());
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    @GetMapping("/client/get-data")
    public ResponseEntity<?> getClientData(){
        try{
            ClientDTO dto = clientService.getClientData(authService.getLogin());
            return ResponseEntity.ok(dto);
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    @DeleteMapping("/client/delete-ticket/{ticketNumber}")
    public ResponseEntity<?> deleteTicket(@PathVariable String ticketNumber){
        try{
            databaseService.deleteBooking(ticketNumber);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    //------------------------------------------------------Getting data------------------------------------------------
    @GetMapping("/trip/{tripId}")
    public ResponseEntity<?> getSeats(@PathVariable int tripId){
        try{
            TrainDTO dto = databaseService.getTrain(tripId);
            return ResponseEntity.ok(dto);
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    @GetMapping("/trips")
    public ResponseEntity<?> getTrips(){
        try{
            ArrayList<TripDTO> list = databaseService.getTrips();
            return ResponseEntity.ok(list);
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    @GetMapping("/trip-info/{tripId}")
    public ResponseEntity<?> getTripInfo(@PathVariable int tripId){
        try{
            TripDTO dto = databaseService.getTrip(tripId);
            return ResponseEntity.ok(dto);
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

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

    @GetMapping("/get-cities")
    public ResponseEntity<?> getCities(){
        try{
            ArrayList<CityDTO> cities = databaseService.getCities();
            return ResponseEntity.ok(cities);
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    @GetMapping("/get-train-numbers")
    public ResponseEntity<?> getTrainNumbers(){
        try{
            ArrayList<TrainNumberDTO> trainNumbers = databaseService.getTrainNumbers();
            return ResponseEntity.ok(trainNumbers);
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    //------------------------------------------------------Adding data-------------------------------------------------
    @PostMapping("/add-city")
    public ResponseEntity<?> addCity(@RequestBody CityDTO dto){
        try{
            databaseService.addCity(dto);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    @PostMapping("/add-carriage-type")
    public ResponseEntity<?> addCarriageType(@RequestBody CarriageTypeDTO dto){
        try{
            databaseService.addCarriageType(dto);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    @PostMapping("/add-train")
    public ResponseEntity<?> addTrain(@RequestBody @Valid TrainDTO dto){
        try{
            databaseService.addTrain(dto);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    @PostMapping("/add-trip")
    public ResponseEntity<?> addTrip(@RequestBody TripDTO dto){
        try{
            databaseService.addTrip(dto);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

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

    @GetMapping("/client/test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok().build();
    }
}
