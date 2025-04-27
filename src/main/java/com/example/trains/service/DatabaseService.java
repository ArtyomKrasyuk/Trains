package com.example.trains.service;

import com.example.trains.dto.*;
import com.example.trains.models.*;
import com.example.trains.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

@Service
@Transactional
public class DatabaseService {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private CarriageRepository carriageRepository;
    @Autowired
    private CarriageTypeRepository carriageTypeRepository;
    @Autowired
    private TrainRepository trainRepository;
    @Autowired
    private TripRepository tripRepository;

    public TrainDTO getTrain(int tripId){
        Trip trip = tripRepository.findById(tripId).orElseThrow();
        double city_factor = trip.getDestination().getRangeFactor();
        Train train = trip.getTrain();

        ArrayList<CarriageDTO> carriageDTOS = new ArrayList<>();
        for(Carriage carriage: train.getCarriages()){
            ArrayList<PlaceDTO> placeDTOS = new ArrayList<>();
            for(Place place: carriage.getPlaces()){
                placeDTOS.add(new PlaceDTO(
                        place.getPosition(),
                        carriage.getType().getPlacePrice() * city_factor * place.getComfortFactor(),
                        place.getPassengerGender()
                ));
            }
            placeDTOS.sort(Comparator.comparing(PlaceDTO::getPosition));
            carriageDTOS.add(new CarriageDTO(
                    carriage.getCarriageNumber(),
                    carriage.getType().getTypeName(),
                    carriage.getNumberOfSeats(),
                    carriage.getTopBlockWidth(),
                    carriage.getBottomBlockWidth(),
                    placeDTOS
            ));
        }

        return new TrainDTO(train.getTrainId(), train.getTrainName(), carriageDTOS);
    }

    public void addCity(CityDTO dto){
        cityRepository.save(new City(dto.getCityName(), dto.getRangeFactor()));
    }

    public void addCarriageType(CarriageTypeDTO dto){
        carriageTypeRepository.save(new CarriageType(dto.getTypeName(), dto.getPlacePrice()));
    }

    public void addTrain(TrainDTO dto){
        Train train = trainRepository.save(new Train(dto.getTrainName()));
        for(CarriageDTO carriageDTO: dto.getCarriages()){
            CarriageType carriageType = carriageTypeRepository.findByTypeName(carriageDTO.getType()).orElseThrow();
            Carriage carriage = carriageRepository.save(new Carriage(
                    carriageDTO.getNumberOfSeats(),
                    carriageDTO.getNumber(),
                    carriageDTO.getTopBlockWidth(),
                    carriageDTO.getBottomBlockWidth(),
                    train,
                    carriageType
            ));
            for(PlaceDTO placeDTO: carriageDTO.getPlaces()){
                placeRepository.save(new Place(
                        carriage,
                        placeDTO.getGender(),
                        placeDTO.getPrice() / carriage.getType().getPlacePrice(),
                        placeDTO.getPosition()
                ));
            }
        }
    }

    public void addTrip(TripDTO dto){
        Train train = trainRepository.findById(dto.getTrainId()).orElseThrow();
        City destination = cityRepository.findByCityName(dto.getDestination()).orElseThrow();
        String pattern = "dd.MM.yyyy HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime departureTimeString = LocalDateTime.from(formatter.parse(dto.getDepartureTime()));
        LocalDateTime arrivalTimeString = LocalDateTime.from(formatter.parse(dto.getArrivalTime()));
        Timestamp departureTime = Timestamp.valueOf(departureTimeString);
        Timestamp arrivalTime = Timestamp.valueOf(arrivalTimeString);
        tripRepository.save(new Trip(train, destination, departureTime, arrivalTime));
    }
}
