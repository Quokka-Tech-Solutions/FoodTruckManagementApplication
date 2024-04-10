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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
        when(foodTruckRepository.findAllByUser(mockUser)).thenReturn(expectedFoodTrucks);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));

        // Act
        List<FoodTruck> actualFoodTrucks = foodTruckService.getAllFoodTrucksByUserId(mockUser.getUserId());

        // Assert
        Assertions.assertEquals(expectedFoodTrucks, actualFoodTrucks);
    }

    @Test
    void updateFoodTruck_shouldUpdateFoodTruckAndReturnUpdatedTruck() {
        // Arrange
        Long truckId = 1L;
        User mockUser = new User(1L, "user", "password", "OWNER", "TestCompany");
        FoodTruck existingTruck = new FoodTruck(truckId, "FoodTruck1", mockUser);
        FoodTruck updatedTruck = new FoodTruck(truckId, "UpdatedFoodTruck", new User(2L, "newUser", "newPassword", "OWNER", "NewTestCompany"));

        when(foodTruckRepository.findById(truckId)).thenReturn(Optional.of(existingTruck));
        when(foodTruckRepository.save(any(FoodTruck.class))).thenReturn(updatedTruck);

        // Act
        FoodTruck result = foodTruckService.updateFoodTruck(truckId, updatedTruck);

        // Assert
        assertThat(result).isEqualTo(updatedTruck);
        verify(foodTruckRepository, times(1)).findById(truckId);
        verify(foodTruckRepository, times(1)).save(any(FoodTruck.class));
    }

    @Test
    void updateFoodTruck_shouldThrowExceptionWhenTruckNotFound() {
        // Arrange
        Long truckId = 1L;
        FoodTruck updatedTruck = new FoodTruck(truckId, "UpdatedFoodTruck", new User(2L, "newUser", "newPassword", "OWNER", "NewTestCompany"));

        when(foodTruckRepository.findById(truckId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> foodTruckService.updateFoodTruck(truckId, updatedTruck))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Food truck with ID " + truckId + " not found");

        verify(foodTruckRepository, times(1)).findById(truckId);
        verify(foodTruckRepository, never()).save(any(FoodTruck.class));
    }


}
