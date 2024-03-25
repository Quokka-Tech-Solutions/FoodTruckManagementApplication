package com.quokkatech.foodtruckmanagement.application.services;

import com.quokkatech.foodtruckmanagement.application.exceptions.TruckAlreadyExistsException;
import com.quokkatech.foodtruckmanagement.domain.entities.FoodTruck;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.domain.repositories.FoodTruckRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FoodTruckService {

    private FoodTruckRepository foodTruckRepository;

    public FoodTruckService(FoodTruckRepository foodTruckRepository){
        this.foodTruckRepository = foodTruckRepository;
    }

    public Optional<FoodTruck> findById(long id) {
        return foodTruckRepository.findById(id);
    }

    public Optional<FoodTruck> findByName(String name){return Optional.ofNullable(foodTruckRepository.findByName(name));}
    public void createFoodTruck(FoodTruck foodTruck){
        foodTruckRepository.save(foodTruck);
    }


}
