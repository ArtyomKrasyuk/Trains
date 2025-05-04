package com.example.trains.repos;

import com.example.trains.models.Trip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TripRepository extends CrudRepository<Trip, Integer> {
    @Query(value = "SELECT * FROM trip WHERE departure_time >= (NOW() + INTERVAL '6 hours') ORDER BY departure_time;", nativeQuery = true)
    Iterable<Trip> findAllUpcomingTrips();
}
