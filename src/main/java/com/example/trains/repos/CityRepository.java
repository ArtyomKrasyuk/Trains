package com.example.trains.repos;

import com.example.trains.models.City;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CityRepository extends CrudRepository<City, Integer> {
    Optional<City> findByCityName(String cityName);
}
