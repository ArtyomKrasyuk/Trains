package com.example.trains.controllers;

import com.example.trains.dto.*;
import com.example.trains.service.DatabaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @Autowired
    private DatabaseService databaseService;

    // Метод обработки запроса добавления города
    @PostMapping("admin/city")
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
    @PostMapping("admin/carriage-type")
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
    @PostMapping("/admin/train")
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
    @PatchMapping("/admin/train/{trainId}")
    public ResponseEntity<?> changeTrain(@RequestBody @Valid TrainChangeDTO dto, @PathVariable int trainId){
        try{
            databaseService.changeTrain(dto, trainId);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки запроса изменения рейса
    @PatchMapping("/admin/trip/{tripId}")
    public ResponseEntity<?> changeTrip(@RequestBody TripInputDTO dto, @PathVariable int tripId){
        try{
            databaseService.updateTrip(dto, tripId);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки запроса добавления рейса
    @PostMapping("/admin/trip")
    public ResponseEntity<?> addTrip(@RequestBody TripInputDTO dto){
        try{
            databaseService.addTrip(dto);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    // Метод обработки запроса проверки авторизации администратора
    @GetMapping("/admin/test")
    public ResponseEntity<?> testAdmin(){
        return ResponseEntity.ok().build();
    }

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
    @DeleteMapping("/admin/train/{trainId}")
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
