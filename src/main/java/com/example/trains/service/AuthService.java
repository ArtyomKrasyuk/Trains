package com.example.trains.service;

import com.example.trains.dto.ClientLoginDTO;
import com.example.trains.dto.ClientRegistrationDTO;
import com.example.trains.exceptions.LoginExistsException;
import com.example.trains.exceptions.NotAdminException;
import com.example.trains.models.Client;
import com.example.trains.repos.ClientRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
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
        client.setRole(Client.Role.ROLE_CLIENT);
        Client entity = clientRepository.save(client);

        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(String.valueOf(entity.getClientId()), dto.getPassword());
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
        int id = getUserIdFromDatabase(dto.getLogin());
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(String.valueOf(id), dto.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        if(authentication.isAuthenticated()){
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
        }
        return authentication;
    }

    public Authentication loginAdmin(ClientLoginDTO dto, HttpServletRequest request, HttpServletResponse response) throws Exception{
        int id = getUserIdFromDatabase(dto.getLogin());
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(String.valueOf(id), dto.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        if(authentication.isAuthenticated()){
            for(GrantedAuthority authority: authentication.getAuthorities()){
                if(!authority.getAuthority().equals("ROLE_ADMIN")) throw new NotAdminException("Это не аккаунт админа");
            }
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
        }
        return authentication;
    }

    public int getLogin(){
        return Integer.parseInt(securityContextHolderStrategy.getContext().getAuthentication().getName());
    }

    public int getUserIdFromDatabase(String login){
        Client client =  clientRepository.findByLogin(login).orElseThrow();
        return client.getClientId();
    }
}
