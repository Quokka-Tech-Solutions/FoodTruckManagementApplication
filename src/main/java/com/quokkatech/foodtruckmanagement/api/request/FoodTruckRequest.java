package com.quokkatech.foodtruckmanagement.api.request;

import com.quokkatech.foodtruckmanagement.domain.entities.FoodTruck;
import com.quokkatech.foodtruckmanagement.domain.entities.User;

public class FoodTruckRequest {
    private long truckId;
    private String truckName;

    private User user;

    public FoodTruckRequest() {}

    public FoodTruckRequest(long truckId, String truckName, User user) {
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

    public FoodTruck toFoodTruck(){
        return new FoodTruck(truckId,truckName,user);
    }
}
