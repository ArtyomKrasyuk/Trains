package com.example.trains.service;

import com.example.trains.dto.ClientDTO;
import com.example.trains.exceptions.LoginExistsException;
import com.example.trains.exceptions.PasswordMismatch;
import com.example.trains.models.Client;
import com.example.trains.repos.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
@Transactional
public class ClientService {
    @Autowired
    private ClientRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void changeFirstname(String login, String firstname){
        Client client = repository.findByLogin(login).orElseThrow();
        client.setFirstname(firstname);
        repository.save(client);
    }

    public void changeLastname(String login, String lastname){
        Client client = repository.findByLogin(login).orElseThrow();
        client.setLastname(lastname);
        repository.save(client);
    }

    public void changePatronymic(String login, String patronymic){
        Client client = repository.findByLogin(login).orElseThrow();
        client.setPatronymic(patronymic);
        repository.save(client);
    }

    public void changeLogin(String login, String newLogin) throws LoginExistsException{
        if(repository.existsByLogin(newLogin)) throw new LoginExistsException("Такой логин уже существует");
        Client client = repository.findByLogin(login).orElseThrow();
        client.setLogin(newLogin);
        repository.save(client);
    }

    public void changeBirthday(String login, Date birthday){
        Client client = repository.findByLogin(login).orElseThrow();
        client.setBirthday(birthday);
        repository.save(client);
    }

    public void changePhone(String login, String phone){
        Client client = repository.findByLogin(login).orElseThrow();
        client.setPhone(phone);
        repository.save(client);
    }

    public void changeGender(String login, short gender){
        Client client = repository.findByLogin(login).orElseThrow();
        client.setGender(gender);
        repository.save(client);
    }

    public void changePassword(String login, String oldPassword, String newPassword) throws PasswordMismatch {
        Client client = repository.findByLogin(login).orElseThrow();
        if(!passwordEncoder.matches(oldPassword, client.getPassword())) throw new PasswordMismatch("Старый пароль не совпадает с действующим");
        client.setPassword(passwordEncoder.encode(newPassword));
        repository.save(client);
    }

    public ClientDTO getClientData(String login){
        Client client = repository.findByLogin(login).orElseThrow();
        String birthday = "";
        if(client.getBirthday() != null) birthday = client.getBirthday().toString();
        String phone = "";
        if(client.getPhone() != null) phone = client.getPhone();
        return new ClientDTO(
                client.getFirstname(),
                client.getLastname(),
                client.getPatronymic(),
                client.getLogin(),
                client.getGender(),
                phone,
                birthday
        );
    }
}
