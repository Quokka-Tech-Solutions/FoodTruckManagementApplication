package com.quokkatech.foodtruckmanagement.application.services;

import com.quokkatech.foodtruckmanagement.domain.entities.FoodTruck;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.domain.repositories.FoodTruckRepository;
import com.quokkatech.foodtruckmanagement.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FoodTruckService {

    private FoodTruckRepository foodTruckRepository;
    private UserRepository userRepository;

    public FoodTruckService(FoodTruckRepository foodTruckRepository, UserRepository userRepository){
        this.foodTruckRepository = foodTruckRepository;
        this.userRepository = userRepository;
    }

    public Optional<FoodTruck> findById(long id) {
        return foodTruckRepository.findById(id);
    }

    public Optional<FoodTruck> findByName(String name){return Optional.ofNullable(foodTruckRepository.findByName(name));}
    public void createFoodTruck(FoodTruck foodTruck){
        foodTruckRepository.save(foodTruck);
    }


    public List<FoodTruck> getAllFoodTrucksByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(foodTruckRepository::findAllByUser).orElse(Collections.emptyList());
    }


}
