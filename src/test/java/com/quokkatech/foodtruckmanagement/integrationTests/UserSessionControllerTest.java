package com.quokkatech.foodtruckmanagement.integrationTests;

import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.application.services.UserSessionService;
import com.quokkatech.foodtruckmanagement.integrationTests.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestConfig.class)
public class UserSessionControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @MockBean
    private UserSessionService userService;


    @Test
    void shouldReturnAUserById(){
        User mockUser = new User();
        when(userService.findById(1L)).thenReturn(Optional.of(mockUser));

        ResponseEntity<User> response = restTemplate.getForEntity("/userSessions/1", User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
    @Test
    void shouldReturnAUserByUsername(){
        User mockUser = new User();
        when(userService.findByUsername("username")).thenReturn(Optional.of(mockUser));

        ResponseEntity<User> response = restTemplate.getForEntity("/userSessions/username/username", User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getAllUsersShouldReturnAllUsers() {
        // Mocking a list of users
        User user1 = new User();
        User user2 = new User();
        when(userService.findAllUsers()).thenReturn(Arrays.asList(user1, user2));


        ResponseEntity<User[]> response = restTemplate.getForEntity("/userSessions", User[].class);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        User[] users = response.getBody();
        assertThat(users).isNotNull();
        assertThat(users).hasSize(2);
    }
}
