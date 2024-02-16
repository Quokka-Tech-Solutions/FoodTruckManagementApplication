package com.quokkatech.foodtruckmanagement;

import com.quokkatech.foodtruckmanagement.api.controllers.RegistrationController;
import com.quokkatech.foodtruckmanagement.api.dto.UserSessionDTO;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.application.exceptions.UsernameAlreadyExistsException;
import com.quokkatech.foodtruckmanagement.domain.repositories.UserRepository;
import com.quokkatech.foodtruckmanagement.application.services.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
        UserSessionDTO userRepresentation = new UserSessionDTO("testuser", "testpassword", "USER", "Test Company");

        ResponseEntity<UserSessionDTO> response = restTemplate.postForEntity("/userRegistrations", userRepresentation, UserSessionDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("testuser");

    }

    @Test
    void registerUserShouldThrowUsernameAlreadyExistsException() {
        UserSessionDTO userRepresentation = new UserSessionDTO("existinguser", "testpassword", "USER", "Test Company");

        doThrow(UsernameAlreadyExistsException.class)
                .when(registrationService)
                .registerUser(userRepresentation.toUser());

        ResponseEntity<Void> response = restTemplate.postForEntity("/userRegistrations", userRepresentation, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }
}
