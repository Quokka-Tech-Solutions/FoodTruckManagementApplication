package com.quokkatech.foodtruckmanagement.domain.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class FoodTruck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long truckId;
    private String name;

    @ManyToOne
    private User user;

    @ManyToOne
    private Menu menu;

    public FoodTruck() {}

    public FoodTruck(long truckId, String name, User user, Menu menu) {
        this.truckId = truckId;
        this.name = name;
        this.user = user;
        this.menu = menu;
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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodTruck foodTruck = (FoodTruck) o;
        return Objects.equals(truckId, foodTruck.truckId) && Objects.equals(name, foodTruck.name) && Objects.equals(user, foodTruck.user) && Objects.equals(menu, foodTruck.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(truckId, name, user, menu);
    }
}
