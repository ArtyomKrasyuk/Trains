package com.example.trains.repos;

import com.example.trains.models.Booking;
import com.example.trains.models.Client;
import com.example.trains.models.Trip;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookingRepository extends CrudRepository<Booking, Integer> {
    Iterable<Booking> findByTrip(Trip trip);
    Iterable<Booking> findByClient(Client client);
    Optional<Booking> findByTicketNumber(String ticketNumber);
}
