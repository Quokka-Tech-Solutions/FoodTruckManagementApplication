package com.quokkatech.foodtruckmanagement.domain.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long menuId;
    @ElementCollection
    private List<Item> items;
    @OneToMany(mappedBy = "menu")
    private List<FoodTruck> foodTrucks;

    public Menu() {
    }

    public Menu(Long menuId, List<Item> items, List<FoodTruck> foodTrucks) {
        this.menuId = menuId;
        this.items = items;
        this.foodTrucks = foodTrucks;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<FoodTruck> getFoodTrucks() {
        return foodTrucks;
    }

    public void setFoodTrucks(List<FoodTruck> foodTrucks) {
        this.foodTrucks = foodTrucks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(menuId, menu.menuId) && Objects.equals(items, menu.items) && Objects.equals(foodTrucks, menu.foodTrucks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, items, foodTrucks);
    }
}
