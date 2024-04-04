package com.quokkatech.foodtruckmanagement.unitTests;

import com.quokkatech.foodtruckmanagement.application.services.FoodTruckService;
import com.quokkatech.foodtruckmanagement.domain.entities.FoodTruck;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.domain.repositories.FoodTruckRepository;
import com.quokkatech.foodtruckmanagement.domain.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FoodTruckServiceTest {
    @Mock
    private FoodTruckRepository foodTruckRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private FoodTruckService foodTruckService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createFoodTruck(){
        FoodTruck foodTruck = new FoodTruck(1L,"FoodTruck1", new User(1L,"user","password","OWNER", "TestCompany"));

        foodTruckService.createFoodTruck(foodTruck);

        verify(foodTruckRepository, times(1)).save(foodTruck);
    }

    @Test
    void getAllFoodTrucksByUserId(){
        User mockUser = new User(1L, "user", "password", "OWNER", "TestCompany");
        FoodTruck mockTruck1 = new FoodTruck(1L, "FoodTruck1", mockUser);
        FoodTruck mockTruck2 = new FoodTruck(2L, "FoodTruck2", mockUser);
        List<FoodTruck> expectedFoodTrucks = Arrays.asList(mockTruck1, mockTruck2);

        // Mock the repository behavior
        Mockito.when(foodTruckRepository.findAllByUser(mockUser)).thenReturn(expectedFoodTrucks);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));

        // Act
        List<FoodTruck> actualFoodTrucks = foodTruckService.getAllFoodTrucksByUserId(mockUser.getUserId());

        // Assert
        Assertions.assertEquals(expectedFoodTrucks, actualFoodTrucks);
    }

}
