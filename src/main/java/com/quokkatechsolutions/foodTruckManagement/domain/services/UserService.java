package com.quokkatechsolutions.foodTruckManagement.domain.services;

import com.quokkatechsolutions.foodTruckManagement.domain.entities.User;
import com.quokkatechsolutions.foodTruckManagement.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }
}
