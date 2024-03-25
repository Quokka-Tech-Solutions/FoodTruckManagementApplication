package com.quokkatech.foodtruckmanagement.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class FoodTruck {
    @Id
    private Long truckId;
    private String name;

    @ManyToOne
    private User user;

    public FoodTruck() {}

    public FoodTruck(long truckId, String name, User user) {
        this.truckId = truckId;
        this.name = name;
        this.user = user;
    }

    public long getTruckId() {
        return truckId;
    }

    public void setTruckId(long truckId) {
        this.truckId = truckId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
