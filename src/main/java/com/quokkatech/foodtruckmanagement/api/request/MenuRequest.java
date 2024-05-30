package com.quokkatech.foodtruckmanagement.api.request;

import com.quokkatech.foodtruckmanagement.domain.entities.FoodTruck;
import com.quokkatech.foodtruckmanagement.domain.entities.Item;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.OneToMany;

import java.util.List;

public class MenuRequest {
    private Long menuId;
    private List<Item> items;
    private List<FoodTruck> foodTrucks;

    public MenuRequest() {
    }

    public MenuRequest(Long menuId, List<Item> items, List<FoodTruck> foodTrucks) {
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
}
