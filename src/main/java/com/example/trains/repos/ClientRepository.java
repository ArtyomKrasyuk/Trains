package com.example.trains.repos;

import com.example.trains.models.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Integer> {
    Optional<Client> findByLogin(String login);
    boolean existsByLogin(String login);
}
