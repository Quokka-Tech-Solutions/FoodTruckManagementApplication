package com.quokkatech.foodtruckmanagement.api.representations;

import com.quokkatech.foodtruckmanagement.domain.entities.User;

public class UserRepresentation {

    private String username;
    private String password;
    private String role;
    private String company;

    // Constructors, getters, and setters

    public UserRepresentation() {
    }

    public UserRepresentation(String username, String password, String role, String company) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.company = company;
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
    public User toUser() {
        // Create and return a User entity using the data from the representation
        return new User(null, username, password, role, company);
    }
}
