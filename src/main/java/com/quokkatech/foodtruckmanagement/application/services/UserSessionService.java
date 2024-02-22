package com.quokkatech.foodtruckmanagement.application.services;

import com.quokkatech.foodtruckmanagement.application.exceptions.UsernameDoesNotExistException;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.domain.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSessionService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserSessionService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }
    public Optional<User> findByUsername(String username){return Optional.ofNullable(userRepository.findByUsername(username));}

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public String createUserSession(User user) {
        Optional<User> userOptional = findByUsername(user.getUsername());

        if (userOptional.isPresent()) {
            User storedUser = userOptional.get();

            // Validate the provided password against the stored, encoded password
            if (passwordEncoder.matches(user.getPassword(), storedUser.getPassword())) {
                return "Bearer p2k23jloreik54938382883.939029";
            }
        }

        throw new UsernameDoesNotExistException("Invalid username or password");
    }
}
