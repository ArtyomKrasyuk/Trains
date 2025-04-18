package com.example.trains.service;

import com.example.trains.dto.ClientLoginDTO;
import com.example.trains.dto.ClientRegistrationDTO;
import com.example.trains.exceptions.LoginExistsException;
import com.example.trains.models.Client;
import com.example.trains.repos.ClientRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final SecurityContextHolderStrategy securityContextHolderStrategy =
            SecurityContextHolder.getContextHolderStrategy();
    @Autowired
    private SecurityContextRepository securityContextRepository;

    public Authentication registration(ClientRegistrationDTO dto, HttpServletRequest request, HttpServletResponse response) throws LoginExistsException {
        if(clientRepository.existsByLogin(dto.getLogin())) throw new LoginExistsException("Такой логин уже существует");
        Client client = new Client();
        client.setFirstname(dto.getFirstname());
        client.setLastname(dto.getLastname());
        client.setPatronymic(dto.getPatronymic());
        client.setLogin(dto.getLogin());
        client.setPassword(passwordEncoder.encode(dto.getPassword()));
        client.setRole(Client.Role.CLIENT);
        clientRepository.save(client);

        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(dto.getLogin(), dto.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        if(authentication.isAuthenticated()){
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
        }
        return authentication;
    }

    public Authentication login(ClientLoginDTO dto, HttpServletRequest request, HttpServletResponse response){
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(dto.getLogin(), dto.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        if(authentication.isAuthenticated()){
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
        }
        return authentication;
    }

    public String getLogin(){
        return securityContextHolderStrategy.getContext().getAuthentication().getName();
    }
}
