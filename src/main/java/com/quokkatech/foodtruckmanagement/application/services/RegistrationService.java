package com.quokkatech.foodtruckmanagement.application.services;

import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.application.exceptions.UsernameAlreadyExistsException;
import com.quokkatech.foodtruckmanagement.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private UserRepository userRepository;
    public RegistrationService(UserRepository userRepository){
        this.userRepository=userRepository;
    }


    public void registerUser(User user){
        if (userRepository.existsByUsername(user.getUsername())){
            throw new UsernameAlreadyExistsException("Username already exists: " + user.getUsername());
        }
        userRepository.save(user);
    }
}
