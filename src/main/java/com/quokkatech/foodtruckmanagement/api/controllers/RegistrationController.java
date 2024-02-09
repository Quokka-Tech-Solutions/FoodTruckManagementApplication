package com.quokkatech.foodtruckmanagement.api.controllers;

import com.quokkatech.foodtruckmanagement.api.representations.UserRepresentation;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.domain.exceptions.UsernameAlreadyExistsException;
import com.quokkatech.foodtruckmanagement.domain.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userRegistrations")
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService=registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRepresentation> registerUser(@RequestBody UserRepresentation userRepresentation) {
        try {
            User user = new User();
            user.setUsername(userRepresentation.getUsername());
            user.setPassword(userRepresentation.getPassword());
            user.setRole(userRepresentation.getRole());
            user.setCompany(userRepresentation.getCompany());

            registrationService.registerUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(userRepresentation);
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userRepresentation);
        }
    }
}
