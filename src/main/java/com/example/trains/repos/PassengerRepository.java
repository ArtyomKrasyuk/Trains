package com.example.trains.repos;

import com.example.trains.models.Passenger;
import org.springframework.data.repository.CrudRepository;

public interface PassengerRepository extends CrudRepository<Passenger,Integer> {
}
