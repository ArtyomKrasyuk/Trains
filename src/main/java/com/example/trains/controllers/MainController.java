package com.example.trains.controllers;

import com.example.trains.dto.*;
import com.example.trains.exceptions.LoginExistsException;
import com.example.trains.exceptions.NotAdminException;
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

    //------------------------------------------------------Личный кабинет--------------------------------------------

    // Метод обработки изменения имени
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

    // Метод обработки изменения фамилии
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

    // Метод обработки изменения отчества
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

    // Метод обработки изменения электронной почты
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

    // Метод обработки изменения даты рождения
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

    // Метод обработки изменения номера телефона
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

    // Метод обработки изменения пола
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

    // Метод обработки изменения пароля
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

    // Метод обработки запроса данных пользователя
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

    // Метод обработки запроса удаления билета
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

    //------------------------------------------------------Получение данных-----------------------------------------

    // Метод обработки запроса рейса
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

    // Метод обработки запроса поезда
    @GetMapping("/train/{trainId}")
    public ResponseEntity<?> getTrainAdmin(@PathVariable int trainId){
        try{
            TrainDTO dto = databaseService.getTrainAdmin(trainId);
            return ResponseEntity.ok(dto);
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки запроса рейсов
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

    // Метод обработки запроса рейсов с ценами
    @GetMapping("/trips-with-prices")
    public ResponseEntity<?> getTripsWithPrices(){
        try{
            ArrayList<TripWithPricesDTO> list = databaseService.getTripsWithPrices();
            return ResponseEntity.ok(list);
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки запроса информации о рейсе
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

    // Метод обработки запроса списка городов
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

    // Метод обработки запроса списка номеров поездов
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

    //----------------------------------------------Добавление и изменение------------------------------------------

    // Метод обработки запроса добавления города
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

    // Метод обработки запроса добавления типа вагона
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

    // Метод обработки запроса добавления поезда
    @PostMapping("/admin/add-train")
    public ResponseEntity<?> addTrain(@RequestBody @Valid TrainInputDTO dto){
        try{
            databaseService.addTrain(dto);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки запроса изменения поезда
    @PatchMapping("/admin/change-train")
    public ResponseEntity<?> changeTrain(@RequestBody @Valid TrainInputDTO dto){;
        try{
            databaseService.changeTrain(dto);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки запроса изменения рейса
    @PatchMapping("/admin/change-trip")
    public ResponseEntity<?> changeTrip(@RequestBody TripDTO dto){
        try{
            databaseService.updateTrip(dto);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки запроса добавления рейса
    @PostMapping("/admin/add-trip")
    public ResponseEntity<?> addTrip(@RequestBody TripDTO dto){
        try{
            databaseService.addTrip(dto);
            return ResponseEntity.ok().build();
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

    // Метод обработки запроса проверки авторизации администратора
    @GetMapping("/admin/test")
    public ResponseEntity<?> testAdmin(){
        return ResponseEntity.ok().build();
    }

    //----------------------------------------------Удаление данных-------------------------------------------------

    // Метод обработки запроса удаления рейса
    @DeleteMapping("/admin/trip/{tripId}")
    public ResponseEntity<?> deleteTrip(@PathVariable int tripId){
        try{
            databaseService.deleteTrip(tripId);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки запроса удаления поезда
    @DeleteMapping("/train/{trainId}")
    public ResponseEntity<?> deleteTrain(@PathVariable int trainId){
        try{
            databaseService.deleteTrain(trainId);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }
}
