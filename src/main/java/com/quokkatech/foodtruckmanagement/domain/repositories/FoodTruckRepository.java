package com.quokkatech.foodtruckmanagement.domain.repositories;

import com.quokkatech.foodtruckmanagement.domain.entities.FoodTruck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodTruckRepository extends JpaRepository<FoodTruck,Long> {

    FoodTruck findByName(String name);
}
