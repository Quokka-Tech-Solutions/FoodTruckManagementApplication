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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;

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

        ResponseEntity<FoodTruckResponse> response = restTemplate.postForEntity("/truckRegistrations", foodTruckRequest, FoodTruckResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTruckName()).isEqualTo("FoodTruck1");
    }

}
