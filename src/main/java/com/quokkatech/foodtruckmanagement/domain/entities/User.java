package com.quokkatech.foodtruckmanagement.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class User{

    @Id
    private final Long userId;
    private final String username;
    private final String password;
    private final String role;
    private final String company;

    public User(){
        this.userId=null;
        this.username=null;
        this.password=null;
        this.role=null;
        this.company=null;
    }

    public User(Long userId, String username, String password, String role, String company) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.company = company;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getCompany() {
        return company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(role, user.role) && Objects.equals(company, user.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, role, company);
    }
}