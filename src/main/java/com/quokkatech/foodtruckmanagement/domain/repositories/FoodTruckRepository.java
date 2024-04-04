package com.quokkatech.foodtruckmanagement.domain.repositories;

import com.quokkatech.foodtruckmanagement.domain.entities.FoodTruck;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodTruckRepository extends JpaRepository<FoodTruck,Long> {

    FoodTruck findByName(String name);

    List<FoodTruck> findAllByUser(User user);
}
