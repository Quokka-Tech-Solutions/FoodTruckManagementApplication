package com.quokkatech.foodtruckmanagement.api.dto;

import com.quokkatech.foodtruckmanagement.api.passwordValidation.ValidPassword;
import com.quokkatech.foodtruckmanagement.domain.entities.User;

public class UserSessionDTO {

    private String username;
    @ValidPassword
    private String password;
    private String role;
    private String company;
    private ProfileDTO profileDTO;

    // Constructors, getters, and setters

    public UserSessionDTO() {
    }

    public UserSessionDTO(String username, String password, String role, String company, ProfileDTO profileDTO) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.company = company;
        this.profileDTO = profileDTO;
    }

    // Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public ProfileDTO getProfileDTO() {
        return profileDTO;
    }

    public void setProfileDTO(ProfileDTO profileDTO) {
        this.profileDTO = profileDTO;
    }

    public User toUser() {
        // Create and return a User entity using the data from the representation
        return new User(null, username, password, role, company);
    }

}
