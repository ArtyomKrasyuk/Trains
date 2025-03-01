package com.example.trains.service;

import com.example.trains.models.Client;
import com.example.trains.repos.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ClientService implements UserDetailsService {
    @Autowired
    private ClientRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client;
        try {
            client = repository.findByLogin(username).orElseThrow();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            throw new UsernameNotFoundException(e.getMessage());
        }
        return client;
    }
}
