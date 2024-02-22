package com.quokkatech.foodtruckmanagement.application.services;

import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.application.exceptions.UsernameAlreadyExistsException;
import com.quokkatech.foodtruckmanagement.domain.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }


    public void registerUser(User user){
        if (userRepository.existsByUsername(user.getUsername())){
            throw new UsernameAlreadyExistsException("Username already exists: " + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
