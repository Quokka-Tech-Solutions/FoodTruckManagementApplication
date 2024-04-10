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

    public FoodTruckService(FoodTruckRepository foodTruckRepository, UserRepository userRepository) {
        this.foodTruckRepository = foodTruckRepository;
        this.userRepository = userRepository;
    }

    public Optional<FoodTruck> findById(long id) {
        return foodTruckRepository.findById(id);
    }

    public Optional<FoodTruck> findByName(String name) {
        return Optional.ofNullable(foodTruckRepository.findByName(name));
    }

    public void createFoodTruck(FoodTruck foodTruck) {
        foodTruckRepository.save(foodTruck);
    }

    public void deleteFoodTruck(long id) {
        foodTruckRepository.deleteById(id);
    }


    public List<FoodTruck> getAllFoodTrucksByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(foodTruckRepository::findAllByUser).orElse(Collections.emptyList());
    }

    public FoodTruck updateFoodTruck(Long truckId, FoodTruck updatedFoodTruck){
        Optional<FoodTruck>existingFoodTruck = foodTruckRepository.findById(truckId);

        if (existingFoodTruck.isPresent()){
            FoodTruck foodTruckToUpdate = existingFoodTruck.get();
            foodTruckToUpdate.setTruckId(truckId);
            foodTruckToUpdate.setName(updatedFoodTruck.getName());
            foodTruckToUpdate.setUser(updatedFoodTruck.getUser());

            FoodTruck updatedTruck = foodTruckRepository.save(foodTruckToUpdate);
            return updatedTruck;
        }
        else {
            throw new NoSuchElementException("Food truck with ID " + truckId + " not found");
        }
    }

}
