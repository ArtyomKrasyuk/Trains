package com.example.trains.repos;

import com.example.trains.models.Train;
import org.springframework.data.repository.CrudRepository;

public interface TrainRepository extends CrudRepository<Train, Integer> {
}
