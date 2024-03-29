package com.quokkatech.foodtruckmanagement.api.controllers;

import com.quokkatech.foodtruckmanagement.api.request.ProfileRequest;
import com.quokkatech.foodtruckmanagement.api.request.UserSessionRequest;
import com.quokkatech.foodtruckmanagement.api.response.UserSessionResponse;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.application.exceptions.UsernameAlreadyExistsException;
import com.quokkatech.foodtruckmanagement.application.services.RegistrationService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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

    @PostMapping("")
    public ResponseEntity<UserSessionResponse> registerUser(@Valid @RequestBody UserSessionRequest userSessionRequest) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            User user = modelMapper.map(userSessionRequest, User.class);
            registrationService.registerUser(user);

            // Check if profile information is present in the request and create the profile
            if (userSessionRequest.getProfileRequest() != null) {
                ProfileRequest profileRequest = userSessionRequest.getProfileRequest();
                registrationService.createProfile(user, profileRequest);
            }

            UserSessionResponse userSessionResponse = new UserSessionResponse(userSessionRequest.getUsername(), userSessionRequest.getRole(), userSessionRequest.getCompany(),userSessionRequest.getProfileRequest());

            return ResponseEntity.status(HttpStatus.CREATED).body(userSessionResponse);
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserSessionResponse(userSessionRequest.getUsername(), userSessionRequest.getRole(), userSessionRequest.getCompany(),userSessionRequest.getProfileRequest()));
        }
    }
}
