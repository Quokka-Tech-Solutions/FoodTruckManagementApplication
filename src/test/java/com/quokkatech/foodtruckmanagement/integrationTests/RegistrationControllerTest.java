package com.quokkatech.foodtruckmanagement.integrationTests;

import com.quokkatech.foodtruckmanagement.api.controllers.RegistrationController;
import com.quokkatech.foodtruckmanagement.api.request.UserSessionRequest;
import com.quokkatech.foodtruckmanagement.api.response.UserSessionResponse;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.application.exceptions.UsernameAlreadyExistsException;
import com.quokkatech.foodtruckmanagement.domain.repositories.UserRepository;
import com.quokkatech.foodtruckmanagement.application.services.RegistrationService;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestConfig.class)
public class RegistrationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RegistrationService registrationService;

    @Autowired
    private RegistrationController registrationController;

    @BeforeEach
    void setUp() {
        // Create a user with the desired username
        User existingUser = new User();
        existingUser.setUsername("existinguser");
        existingUser.setPassword("password");
        existingUser.setRole("USER");
        existingUser.setCompany("Test Company");

        // Save the user to the repository
        userRepository.save(existingUser);
    }

    @Test
    void registerUserShouldReturnCreatedStatus() {
        UserSessionRequest userSessionRequest = new UserSessionRequest("testuser", "Testpassword@1", "USER", "Test Company",null);

        ResponseEntity<UserSessionResponse> response = restTemplate.postForEntity("/userRegistrations", userSessionRequest, UserSessionResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("testuser");

    }

    @Test
    void registerUserShouldThrowUsernameAlreadyExistsException() {
        UserSessionRequest userSessionRequest = new UserSessionRequest("existinguser", "Testpassword@1", "USER", "Test Company",null);

        doThrow(UsernameAlreadyExistsException.class)
                .when(registrationService)
                .registerUser(userSessionRequest.toUser());

        ResponseEntity<Void> response = restTemplate.postForEntity("/userRegistrations", userSessionRequest, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void registerUserShouldReturnCreatedStatusForValidPassword() {
        UserSessionRequest userSessionRequest = new UserSessionRequest("testuser", "Test@1234", "USER", "Test Company",null);

        ResponseEntity<UserSessionResponse> response = restTemplate.postForEntity("/userRegistrations", userSessionRequest, UserSessionResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("testuser");
    }

    @Test
    void registerUserShouldReturnBadRequestForInvalidPassword() {
        UserSessionRequest userSessionRequest = new UserSessionRequest("testuser", "weakpassword", "USER", "Test Company",null);

        ResponseEntity<Void> response = restTemplate.postForEntity("/userRegistrations", userSessionRequest, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
