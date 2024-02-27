package com.quokkatech.foodtruckmanagement.application.services;

import com.quokkatech.foodtruckmanagement.api.dto.ProfileDTO;
import com.quokkatech.foodtruckmanagement.domain.entities.Profile;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.application.exceptions.UsernameAlreadyExistsException;
import com.quokkatech.foodtruckmanagement.domain.repositories.ProfileRepository;
import com.quokkatech.foodtruckmanagement.domain.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private UserRepository userRepository;
    private ProfileRepository profileRepository;
    private PasswordEncoder passwordEncoder;
    public RegistrationService(UserRepository userRepository,ProfileRepository profileRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.profileRepository=profileRepository;
        this.passwordEncoder=passwordEncoder;
    }


    public void registerUser(User user){
        if (userRepository.existsByUsername(user.getUsername())){
            throw new UsernameAlreadyExistsException("Username already exists: " + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void createProfile(User user, ProfileDTO profileDTO) {
        // Create a new profile and associate it with the user
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setProfileId(profileDTO.getProfileId());
        profile.setHomeAddress(profileDTO.getHomeAddress());
        profile.setCompanyAddress(profileDTO.getCompanyAddress());
        profileRepository.save(profile);
    }
}
