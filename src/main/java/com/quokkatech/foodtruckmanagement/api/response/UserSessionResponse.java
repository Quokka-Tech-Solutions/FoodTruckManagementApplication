package com.quokkatech.foodtruckmanagement.api.response;

import com.quokkatech.foodtruckmanagement.api.passwordValidation.ValidPassword;
import com.quokkatech.foodtruckmanagement.api.request.ProfileRequest;
import com.quokkatech.foodtruckmanagement.domain.entities.User;

public class UserSessionResponse {

    private String username;
    private String role;
    private String company;
    private ProfileRequest profileRequest;

    // Constructors, getters, and setters

    public UserSessionResponse() {
    }

    public UserSessionResponse(String username, String role, String company, ProfileRequest profileRequest) {
        this.username = username;
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


}
