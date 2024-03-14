package com.quokkatech.foodtruckmanagement.api.request;

import com.quokkatech.foodtruckmanagement.api.passwordValidation.ValidPassword;
import com.quokkatech.foodtruckmanagement.domain.entities.User;

public class UserSessionRequest {

    private String username;
    @ValidPassword
    private String password;
    private String role;
    private String company;
    private ProfileRequest profileRequest;

    // Constructors, getters, and setters

    public UserSessionRequest() {
    }

    public UserSessionRequest(String username, String password, String role, String company, ProfileRequest profileRequest) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.company = company;
        this.profileRequest = profileRequest;
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

    public ProfileRequest getProfileRequest() {
        return profileRequest;
    }

    public void setProfileRequest(ProfileRequest profileRequest) {
        this.profileRequest = profileRequest;
    }

    public User toUser() {
        // Create and return a User entity using the data from the representation
        return new User(null, username, password, role, company);
    }

}
