package com.quokkatech.foodtruckmanagement.api.controllers;

import com.quokkatech.foodtruckmanagement.api.dto.ProfileDTO;
import com.quokkatech.foodtruckmanagement.api.dto.UserSessionDTO;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.application.exceptions.UsernameAlreadyExistsException;
import com.quokkatech.foodtruckmanagement.application.services.RegistrationService;
import com.quokkatech.foodtruckmanagement.api.passwordEncoding.SecurityConfig;
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
    public ResponseEntity<UserSessionDTO> registerUser(@Valid @RequestBody UserSessionDTO userSessionDTO) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            User user = modelMapper.map(userSessionDTO, User.class);
            registrationService.registerUser(user);

            // Check if profile information is present in the request and create the profile
            if (userSessionDTO.getProfileDTO() != null) {
                ProfileDTO profileDTO = userSessionDTO.getProfileDTO();
                registrationService.createProfile(user, profileDTO);
            }


            return ResponseEntity.status(HttpStatus.CREATED).body(userSessionDTO);
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userSessionDTO);
        }
    }
}
