package com.example.trains.repos;

import com.example.trains.models.Booking;
import com.example.trains.models.Trip;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Integer> {
    Iterable<Booking> findByTrip(Trip trip);
}
