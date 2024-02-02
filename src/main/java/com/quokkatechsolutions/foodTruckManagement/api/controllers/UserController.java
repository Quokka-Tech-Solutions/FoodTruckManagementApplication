package com.quokkatechsolutions.foodTruckManagement.api.controllers;

import com.quokkatechsolutions.foodTruckManagement.domain.services.UserService;
import com.quokkatechsolutions.foodTruckManagement.domain.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/userSessions")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<User> findById(@PathVariable Long requestedId) {
        logger.info("Finding user with ID: {}", requestedId);
        Optional<User> userOptional = userService.findById(requestedId);

        return userOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
