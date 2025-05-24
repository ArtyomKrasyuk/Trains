package com.example.trains.service;

import com.example.trains.dto.*;
import com.example.trains.models.*;
import com.example.trains.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
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
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PassengerRepository passengerRepository;

    public TrainDTO getTrain(int tripId){
        Trip trip = tripRepository.findById(tripId).orElseThrow();
        Iterable<Booking> booking = bookingRepository.findByTrip(trip);
        double city_factor = trip.getDestination().getRangeFactor();
        Train train = trip.getTrain();

        ArrayList<CarriageDTO> carriageDTOS = new ArrayList<>();
        for(Carriage carriage: train.getCarriages()){
            ArrayList<PlaceDTO> placeDTOS = new ArrayList<>();
            for(Place place: carriage.getPlaces()){
                boolean flag = false;
                for(Booking elem: booking){
                    if(elem.getPlace().getPlaceId() == place.getPlaceId()) flag = true;
                }
                placeDTOS.add(new PlaceDTO(
                        place.getPosition(),
                        carriage.getType().getPlacePrice() * city_factor * place.getComfortFactor(),
                        place.getPassengerGender(),
                        flag
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

        carriageDTOS.sort(Comparator.comparing(CarriageDTO::getNumber));

        return new TrainDTO(train.getTrainId(), train.getTrainName(), carriageDTOS);
    }

    public TrainDTO getTrainAdmin(int trainId){
        Train train = trainRepository.findById(trainId).orElseThrow();
        ArrayList<CarriageDTO> carriages = new ArrayList<>();
        for(Carriage carriage: train.getCarriages()){
            CarriageDTO dto = new CarriageDTO(
                    carriage.getCarriageNumber(),
                    carriage.getType().getTypeName(),
                    carriage.getNumberOfSeats(),
                    carriage.getTopBlockWidth(),
                    carriage.getBottomBlockWidth(),
                    null
            );
            carriages.add(dto);
        }
        carriages.sort(Comparator.comparing(CarriageDTO::getNumber));
        return new TrainDTO(
                trainId,
                train.getTrainName(),
                carriages
        );
    }

    public void addCity(CityDTO dto){
        cityRepository.save(new City(dto.getCityName(), dto.getRangeFactor()));
    }

    public void addCarriageType(CarriageTypeDTO dto){
        carriageTypeRepository.save(new CarriageType(dto.getTypeName(), dto.getPlacePrice()));
    }

    public void addTrain(TrainInputDTO dto){
        Train train = trainRepository.save(new Train(dto.getTrainId(), dto.getTrainName()));
        for(CarriageInputDTO carriageDTO: dto.getCarriages()){
            CarriageType carriageType = carriageTypeRepository.findByTypeName(carriageDTO.getType()).orElseThrow();
            Carriage carriage = carriageRepository.save(new Carriage(
                    carriageDTO.getNumberOfSeats(),
                    carriageDTO.getNumber(),
                    carriageDTO.getTopBlockWidth(),
                    carriageDTO.getBottomBlockWidth(),
                    train,
                    carriageType
            ));
            for(PlaceInputDTO placeDTO: carriageDTO.getPlaces()){
                placeRepository.save(new Place(
                        carriage,
                        placeDTO.getGender(),
                        placeDTO.getComfortFactor(),
                        placeDTO.getPosition()
                ));
            }
        }
    }

    public void changeTrain(TrainInputDTO dto){
        Train train = trainRepository.findById(dto.getTrainId()).orElseThrow();
        for(Carriage carriage: train.getCarriages()){
            boolean exists = false;
            for(CarriageInputDTO carriageDTO: dto.getCarriages()){
                if (carriage.getCarriageNumber() == carriageDTO.getNumber()) {
                    exists = true;
                    break;
                }
            }
            if(!exists){
                carriage.setTrain(null);
                train.getCarriages().remove(carriage);
                carriageRepository.save(carriage);
            }
        }

        for(CarriageInputDTO carriageDTO: dto.getCarriages()){
            boolean exists = false;
            for(Carriage carriage: train.getCarriages()){
                if(carriageDTO.getNumber() == carriage.getCarriageNumber()) {
                    exists = true;
                    if(carriageDTO.getNumberOfSeats() != carriage.getNumberOfSeats() ||
                            !carriageDTO.getType().equals(carriage.getType().getTypeName()) ||
                            carriageDTO.getTopBlockWidth() != carriage.getTopBlockWidth() ||
                            carriageDTO.getTopBlockWidth() != carriage.getBottomBlockWidth()
                    ){
                        carriage.setTrain(null);
                        //train.getCarriages().remove(carriage);
                        carriageRepository.save(carriage);
                        CarriageType type = carriageTypeRepository.findByTypeName(carriageDTO.getType()).orElseThrow();
                        Carriage newCarriage = new Carriage(
                                carriageDTO.getNumberOfSeats(),
                                carriageDTO.getNumber(),
                                carriageDTO.getTopBlockWidth(),
                                carriageDTO.getBottomBlockWidth(),
                                train,
                                type
                        );
                        Carriage newEntity = carriageRepository.save(newCarriage);
                        for(PlaceInputDTO placeDTO: carriageDTO.getPlaces()){
                            Place place = new Place(
                                    newEntity,
                                    placeDTO.getGender(),
                                    placeDTO.getComfortFactor(),
                                    placeDTO.getPosition()
                            );
                            placeRepository.save(place);
                        }
                    }
                }
            }
            if(!exists){
                CarriageType type = carriageTypeRepository.findByTypeName(carriageDTO.getType()).orElseThrow();
                Carriage newCarriage = new Carriage(
                        carriageDTO.getNumberOfSeats(),
                        carriageDTO.getNumber(),
                        carriageDTO.getTopBlockWidth(),
                        carriageDTO.getBottomBlockWidth(),
                        train,
                        type
                );
                Carriage newEntity = carriageRepository.save(newCarriage);
                for(PlaceInputDTO placeDTO: carriageDTO.getPlaces()){
                    Place place = new Place(
                            newEntity,
                            placeDTO.getGender(),
                            placeDTO.getComfortFactor(),
                            placeDTO.getPosition()
                    );
                    placeRepository.save(place);
                }
            }
        }
    }

    public void addTrip(TripDTO dto){
        Train train = trainRepository.findById(dto.getTrainId()).orElseThrow();
        City destination = cityRepository.findByCityName(dto.getDestination()).orElseThrow();
        String pattern = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime departureTimeString = LocalDateTime.from(formatter.parse(dto.getDepartureTime()));
        LocalDateTime arrivalTimeString = LocalDateTime.from(formatter.parse(dto.getArrivalTime()));
        Timestamp departureTime = Timestamp.valueOf(departureTimeString);
        Timestamp arrivalTime = Timestamp.valueOf(arrivalTimeString);
        tripRepository.save(new Trip(train, destination, departureTime, arrivalTime));
    }

    public boolean book(BookingsDTO dto, String login){
        Trip trip = tripRepository.findById(dto.getTripId()).orElseThrow();
        Iterable<Booking> booking = bookingRepository.findByTrip(trip);
        for(BookingDTO bookingDTO: dto.getBookings()){
            Place place = null;
            for(Carriage carriage: trip.getTrain().getCarriages()){
                if(carriage.getCarriageNumber() == bookingDTO.getCarriageNumber()){
                    for(Place place1: carriage.getPlaces()){
                        if(place1.getPosition() == bookingDTO.getPosition()) place = place1;
                    }
                }
            }
            if(place == null) return false;
            boolean booked = false;
            for(Booking elem: booking){
                if(elem.getPlace().getPlaceId() == place.getPlaceId()) {
                    booked = true;
                    break;
                }
            }
            if(booked) return false;

            Client client = clientRepository.findByLogin(login).orElseThrow();

            String birthday = dto.getBirthday();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate localDate = LocalDate.parse(birthday, formatter);
            Date date = Date.valueOf(localDate);

            Passenger passenger = new Passenger(
                    dto.getFirstname(),
                    dto.getLastname(),
                    dto.getPatronymic(),
                    date,
                    dto.getGender(),
                    dto.getPhone(),
                    dto.getEmail(),
                    dto.getPassport()
            );

            passengerRepository.save(passenger);

            bookingRepository.save(new Booking(
                    client,
                    place,
                    trip,
                    passenger,
                    bookingDTO.getPrice()
            ));
        }
        return true;
    }

    public ArrayList<TripDTO> getTrips(){
        Iterable<Trip> trips = tripRepository.findAllUpcomingTrips();
        ArrayList<TripDTO> list = new ArrayList<>();
        for(Trip trip: trips){
            int numberOfSeats = 0;
            for(Carriage carriage: trip.getTrain().getCarriages()) numberOfSeats += carriage.getNumberOfSeats();
            boolean hasFreePlaces = trip.getBookings().size() < numberOfSeats;
            TripDTO tripDTO = new TripDTO(
                    trip.getTripId(),
                    trip.getTrain().getTrainId(),
                    trip.getDestination().getCityName(),
                    trip.getDepartureTime().toString(),
                    trip.getArrivalTime().toString(),
                    hasFreePlaces
            );
            list.add(tripDTO);
        }
        return list;
    }

    public TripDTO getTrip(int tripId){
        Trip trip = tripRepository.findById(tripId).orElseThrow();
        return new TripDTO(
                trip.getTripId(),
                trip.getTrain().getTrainId(),
                trip.getDestination().getCityName(),
                trip.getDepartureTime().toString(),
                trip.getArrivalTime().toString(),
                true
        );
    }

    public ArrayList<TicketDTO> getTickets(String login){
        Client client = clientRepository.findByLogin(login).orElseThrow();
        Iterable<Booking> bookings = bookingRepository.findByClient(client);
        ArrayList<TicketDTO> tickets = new ArrayList<>();
        for(Booking booking: bookings){
            Trip trip = booking.getTrip();
            Passenger passenger = booking.getPassenger();
            Place place = booking.getPlace();
            TicketDTO ticket = new TicketDTO(
                    trip.getTrain().getTrainId(),
                    trip.getDestination().getCityName(),
                    trip.getDepartureTime().toString(),
                    trip.getArrivalTime().toString(),
                    passenger.getFirstname(),
                    passenger.getLastname(),
                    passenger.getPatronymic(),
                    passenger.getBirthday().toString(),
                    passenger.getPassportSeriesAndNumber(),
                    booking.getTicketNumber(),
                    place.getCarriage().getCarriageNumber(),
                    place.getPosition(),
                    place.getCarriage().getType().getTypeName()
            );
            tickets.add(ticket);
        }
        return tickets;
    }

    public void deleteBooking(String ticketNumber){
        Booking booking = bookingRepository.findByTicketNumber(ticketNumber).orElseThrow();
        passengerRepository.delete(booking.getPassenger());
        bookingRepository.delete(booking);
    }

    public ArrayList<CityDTO> getCities(){
        Iterable<City> cities = cityRepository.findAll();
        ArrayList<CityDTO> result = new ArrayList<>();
        for(City city: cities){
            CityDTO cityDTO = new CityDTO(city.getCityName(), city.getRangeFactor());
            result.add(cityDTO);
        }
        return result;
    }

    public ArrayList<TrainNumberDTO> getTrainNumbers(){
        Iterable<Train> trains = trainRepository.findAll();
        ArrayList<TrainNumberDTO> numbers = new ArrayList<>();
        for(Train train: trains){
            TrainNumberDTO dto = new TrainNumberDTO(train.getTrainId());
            numbers.add(dto);
        }
        numbers.sort(Comparator.comparing(TrainNumberDTO::getTrainNumber));
        return numbers;
    }

    public void deleteTrip(int tripId){
        tripRepository.deleteById(tripId);
    }

    public void updateTrip(TripDTO dto){
        String pattern = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime departureTimeString = LocalDateTime.from(formatter.parse(dto.getDepartureTime()));
        LocalDateTime arrivalTimeString = LocalDateTime.from(formatter.parse(dto.getArrivalTime()));
        Timestamp departureTime = Timestamp.valueOf(departureTimeString);
        Timestamp arrivalTime = Timestamp.valueOf(arrivalTimeString);
        Trip trip = tripRepository.findById(dto.getTripId()).orElseThrow();
        if(trip.getTrain().getTrainId() != dto.getTrainId()){
            Train train = trainRepository.findById(dto.getTrainId()).orElseThrow();
            trip.setTrain(train);
        }
        if(!trip.getDestination().getCityName().equals(dto.getDestination())){
            City destination = cityRepository.findByCityName(dto.getDestination()).orElseThrow();
            trip.setDestination(destination);
        }
        if(!trip.getDepartureTime().equals(departureTime)){
            trip.setDepartureTime(departureTime);
        }
        if(!trip.getArrivalTime().equals(arrivalTime)){
            trip.setArrivalTime(arrivalTime);
        }
        tripRepository.save(trip);
    }

    public void deleteTrain(int trainId){
        trainRepository.deleteById(trainId);
    }
}
