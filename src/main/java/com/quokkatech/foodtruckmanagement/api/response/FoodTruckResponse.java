package com.quokkatech.foodtruckmanagement.api.response;

import com.quokkatech.foodtruckmanagement.domain.entities.User;

public class FoodTruckResponse {

    private long truckId;
    private String truckName;

    private User user;

    public FoodTruckResponse() {}

    public FoodTruckResponse(long truckId, String truckName, User user) {
        this.truckId = truckId;
        this.truckName = truckName;
        this.user = user;
    }

    public long getTruckId() {
        return truckId;
    }

    public void setTruckId(long truckId) {
        this.truckId = truckId;
    }

    public String getTruckName() {
        return truckName;
    }

    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
