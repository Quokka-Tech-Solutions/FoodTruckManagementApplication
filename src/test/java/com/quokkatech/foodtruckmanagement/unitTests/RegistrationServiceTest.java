package com.quokkatech.foodtruckmanagement.unitTests;

import com.quokkatech.foodtruckmanagement.api.dto.ProfileDTO;
import com.quokkatech.foodtruckmanagement.application.services.RegistrationService;
import com.quokkatech.foodtruckmanagement.domain.entities.Profile;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.application.exceptions.UsernameAlreadyExistsException;
import com.quokkatech.foodtruckmanagement.domain.repositories.ProfileRepository;
import com.quokkatech.foodtruckmanagement.domain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterUser() {
        // Arrange
        User user = new User(null, "testuser", "password", "USER", "Test Company");

        // Mock the behavior
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Act
        registrationService.registerUser(user);

        // Assert
        // Verify that userRepository.save is called once with the correct user object
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegisterUserWithExistingUsername() {
        // Arrange
        User user = new User(null, "existinguser", "password", "USER", "Test Company");

        // Mock the behavior
        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        // Act and Assert
        // Verify that UsernameAlreadyExistsException is thrown
        assertThrows(UsernameAlreadyExistsException.class, () -> registrationService.registerUser(user));
    }

    @Test
    void testCreateProfile() {
        // Arrange
        User user = new User(null, "testuser", "password", "USER", "Test Company");
        ProfileDTO profileDTO = new ProfileDTO(1L, null, null, null);

        // Mock the behavior
        when(profileRepository.save(any())).thenReturn(new Profile());

        // Act
        registrationService.createProfile(user, profileDTO);

        // Assert
        // Verify that profileRepository.save is called once with the correct profile object
        ArgumentCaptor<Profile> profileCaptor = ArgumentCaptor.forClass(Profile.class);
        verify(profileRepository, times(1)).save(profileCaptor.capture());

        // Validate the captured Profile object
        Profile savedProfile = profileCaptor.getValue();
        assert savedProfile != null;
        assert savedProfile.getUser().equals(user);
        assert savedProfile.getProfileId()==(profileDTO.getProfileId());
    }
}
