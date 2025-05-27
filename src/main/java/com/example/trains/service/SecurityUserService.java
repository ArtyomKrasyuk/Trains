package com.example.trains.service;

import com.example.trains.models.Client;
import com.example.trains.repos.ClientRepository;
import com.example.trains.userdetails.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SecurityUserService implements UserDetailsService {
    @Autowired
    private ClientRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client;
        try {
            client = repository.findById(Integer.parseInt(username)).orElseThrow();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            throw new UsernameNotFoundException(e.getMessage());
        }
        return new SecurityUser(client);
    }
}
