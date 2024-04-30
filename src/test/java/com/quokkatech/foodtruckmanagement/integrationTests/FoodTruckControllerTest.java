package com.quokkatech.foodtruckmanagement.integrationTests;

import com.quokkatech.foodtruckmanagement.api.controllers.FoodTruckController;
import com.quokkatech.foodtruckmanagement.api.controllers.RegistrationController;
import com.quokkatech.foodtruckmanagement.api.request.FoodTruckRequest;
import com.quokkatech.foodtruckmanagement.api.response.FoodTruckResponse;
import com.quokkatech.foodtruckmanagement.application.exceptions.TruckAlreadyExistsException;
import com.quokkatech.foodtruckmanagement.application.services.FoodTruckService;
import com.quokkatech.foodtruckmanagement.application.services.RegistrationService;
import com.quokkatech.foodtruckmanagement.domain.entities.FoodTruck;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.domain.repositories.FoodTruckRepository;
import com.quokkatech.foodtruckmanagement.domain.repositories.UserRepository;
import com.quokkatech.foodtruckmanagement.integrationTests.config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestConfig.class)
public class FoodTruckControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private FoodTruckRepository foodTruckRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FoodTruckService foodTruckService;

    @Autowired
    private FoodTruckController foodTruckController;

    @Test
    void registerFoodTruckShouldReturnCreatedStatus(){
        FoodTruckRequest foodTruckRequest = new FoodTruckRequest(1L,"FoodTruck1", new User(1L,"user","password","OWNER", "TestCompany"));

        ResponseEntity<FoodTruckResponse> response = restTemplate.postForEntity("/trucks", foodTruckRequest, FoodTruckResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTruckName()).isEqualTo("FoodTruck1");
    }

    @Test
    void getFoodTruckByIdShouldReturnFoodTruckById(){
        FoodTruck mockTruck = new FoodTruck(1L,"FoodTruck1", new User(1L,"user","password","OWNER", "TestCompany"));
        when(foodTruckService.findById(1L)).thenReturn(Optional.of(mockTruck));

        ResponseEntity<FoodTruck> response = restTemplate.getForEntity("/trucks/truckId/1",FoodTruck.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTruckId()).isEqualTo(1L);
    }

    @Test
    void getFoodTruckByNameShouldReturnFoodTruckByName(){
        FoodTruck mockTruck = new FoodTruck(1L,"FoodTruck1", new User(1L,"user","password","OWNER", "TestCompany"));
        when(foodTruckService.findByName("FoodTruck1")).thenReturn(Optional.of(mockTruck));

        ResponseEntity<FoodTruckResponse> response = restTemplate.getForEntity("/trucks/truckName/FoodTruck1",FoodTruckResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTruckName()).isEqualTo("FoodTruck1");
    }

    @Test
    void getListOfFoodTrucksShouldReturnList(){
        FoodTruck mockTruck1 = new FoodTruck(1L,"FoodTruck1", new User(1L,"user","password","OWNER", "TestCompany"));
        FoodTruck mockTruck2 = new FoodTruck(2L,"FoodTruck2", new User(1L,"user2","password","OWNER", "TestCompany"));
        when(foodTruckService.getAllFoodTrucksByUserId(1L)).thenReturn(Arrays.asList(mockTruck1, mockTruck2));

        ResponseEntity <FoodTruckResponse[]> response = restTemplate.getForEntity("/trucks?userId=1",FoodTruckResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        FoodTruckResponse[] foodTruckResponses = response.getBody();
        assertThat(foodTruckResponses).isNotNull();
        assertThat(foodTruckResponses).hasSize(2);
    }

    @Test
    void updateFoodTruckShouldReturnUpdatedFoodTruck() {
        Long truckId = 1L;
        FoodTruck existingTruck = new FoodTruck(truckId, "FoodTruck1", new User(1L, "user", "password", "OWNER", "TestCompany"));
        FoodTruck updatedTruck = new FoodTruck(truckId, "UpdatedFoodTruck", new User(2L, "newUser", "newPassword", "OWNER", "NewTestCompany"));

        when(foodTruckService.findById(truckId)).thenReturn(Optional.of(existingTruck));
        when(foodTruckService.updateFoodTruck(truckId, updatedTruck)).thenReturn(updatedTruck);

        FoodTruckRequest foodTruckRequest = new FoodTruckRequest(updatedTruck.getTruckId(), updatedTruck.getName(), updatedTruck.getUser());
        ResponseEntity<FoodTruckResponse> response = restTemplate.exchange("/trucks/" + truckId, HttpMethod.PUT, new HttpEntity<>(foodTruckRequest), FoodTruckResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTruckName()).isEqualTo("UpdatedFoodTruck");
        assertThat(response.getBody().getUser().getUsername()).isEqualTo("newUser");
    }

    @Test
    void updateFoodTruckShouldReturnNotFoundWhenTruckDoesNotExist() {
        Long truckId = 1L;
        FoodTruck updatedTruck = new FoodTruck(truckId, "UpdatedFoodTruck", new User(2L, "newUser", "newPassword", "OWNER", "NewTestCompany"));

        when(foodTruckService.findById(truckId)).thenReturn(Optional.empty());

        FoodTruckRequest foodTruckRequest = new FoodTruckRequest(updatedTruck.getTruckId(), updatedTruck.getName(), updatedTruck.getUser());
        ResponseEntity<FoodTruckResponse> response = restTemplate.exchange("/trucks/" + truckId, HttpMethod.PUT, new HttpEntity<>(foodTruckRequest), FoodTruckResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteFoodTruckShouldReturnNoContentWhenTruckIsDeleted() {
        Long truckId = 1L;

        ResponseEntity<Void> response = restTemplate.exchange("/trucks/" + truckId, HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
