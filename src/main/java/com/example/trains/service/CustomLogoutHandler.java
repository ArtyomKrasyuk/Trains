package com.example.trains.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomLogoutHandler implements LogoutHandler {
    private final RedisIndexedSessionRepository repository;

    public CustomLogoutHandler(RedisIndexedSessionRepository repository){
        this.repository = repository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String id = request.getSession(false).getId();
        if(id != null && repository.findById(id) != null){
            repository.deleteById(id);
        }
    }
}
