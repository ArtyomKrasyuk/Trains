package com.example.trains.repos;

import com.example.trains.models.CarriageType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CarriageTypeRepository extends CrudRepository<CarriageType, Integer> {
    Optional<CarriageType> findByTypeName(String typeName);
}
