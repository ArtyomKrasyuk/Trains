package com.example.trains.controllers;

import com.example.trains.dto.*;
import com.example.trains.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class UserController {
    @Autowired
    private DatabaseService databaseService;

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

    // Метод обработки запроса списка городов
    @GetMapping("/cities")
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
    @GetMapping("/train-numbers")
    public ResponseEntity<?> getTrainNumbers(){
        try{
            ArrayList<TrainNumberDTO> trainNumbers = databaseService.getTrainNumbers();
            return ResponseEntity.ok(trainNumbers);
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }
}
