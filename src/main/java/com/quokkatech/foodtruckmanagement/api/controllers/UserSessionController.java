package com.quokkatech.foodtruckmanagement.api.controllers;

import com.quokkatech.foodtruckmanagement.api.request.UserSessionRequest;
import com.quokkatech.foodtruckmanagement.api.response.UserSessionResponse;
import com.quokkatech.foodtruckmanagement.application.exceptions.UsernameDoesNotExistException;
import com.quokkatech.foodtruckmanagement.application.services.UserSessionService;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/userSessions")
public class UserSessionController {

    private static final Logger logger = LoggerFactory.getLogger(UserSessionController.class);
    private final UserSessionService userSessionService;

    public UserSessionController(UserSessionService userSessionService){
        this.userSessionService=userSessionService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findByUserId(@PathVariable Long userId) {
        logger.info("UserController.findById - Finding user with ID: {}", userId);
        Optional<User> userOptional = userSessionService.findById(userId);

        return userOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable ("username") String username){
        logger.info("UserController.findByUsername - Finding user with username: {}", username);
        Optional<User> userOptional = userSessionService.findByUsername(username);
        return userOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<UserSessionResponse> createUserSession(@RequestBody UserSessionRequest userSessionRequest) {
        try {
            User user = new User();
            user.setUsername(userSessionRequest.getUsername());
            user.setPassword(userSessionRequest.getPassword());
            user.setRole(userSessionRequest.getRole());
            user.setCompany(userSessionRequest.getCompany());

            var token = userSessionService.createUserSession(user);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authentication", token);
            userSessionRequest.setPassword("**********");

            UserSessionResponse userSessionResponse = new UserSessionResponse(userSessionRequest.getUsername(), userSessionRequest.getRole(), userSessionRequest.getCompany(), userSessionRequest.getProfileRequest());
            return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(userSessionResponse);
        } catch (UsernameDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserSessionResponse(userSessionRequest.getUsername(), userSessionRequest.getRole(), userSessionRequest.getCompany(), userSessionRequest.getProfileRequest()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new UserSessionResponse(userSessionRequest.getUsername(), userSessionRequest.getRole(), userSessionRequest.getCompany(), userSessionRequest.getProfileRequest()));
        }
    }
    @GetMapping()
    public ResponseEntity<List<User>> getAllUserSessions(){
        List<User> userList = userSessionService.findAllUsers();
        return ResponseEntity.ok(List.of(userList.toArray(new User[0])));
    }

}