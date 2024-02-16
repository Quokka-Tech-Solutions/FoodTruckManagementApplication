package com.quokkatech.foodtruckmanagement.application.services;

import com.quokkatech.foodtruckmanagement.application.exceptions.UsernameDoesNotExistException;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSessionService {

    private UserRepository userRepository;

    public UserSessionService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public String createUserSession(User user) {
        if (userRepository.existsByUsername(user.getUsername())){
            return "Bearer p2k23jloreik54938382883.939029";
        }

        throw new UsernameDoesNotExistException("Username " + user.getUsername() + " does not exist: ");
    }
}
