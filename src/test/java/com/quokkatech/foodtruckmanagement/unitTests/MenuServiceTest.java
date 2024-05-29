package com.quokkatech.foodtruckmanagement.unitTests;

import com.quokkatech.foodtruckmanagement.application.services.MenuService;
import com.quokkatech.foodtruckmanagement.domain.entities.FoodTruck;
import com.quokkatech.foodtruckmanagement.domain.entities.Item;
import com.quokkatech.foodtruckmanagement.domain.entities.Menu;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.domain.repositories.MenuRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class MenuServiceTest {
    @Mock
    private MenuRepository menuRepository;
    @InjectMocks
    private MenuService menuService;

    private static List<Item> items;
    private static List<FoodTruck> foodTrucks;
    @BeforeAll
    static void setup() {
        items = new ArrayList<>();
        Item item1 = new Item(1L, "Burger", 8.99, "Juicy beef patty with lettuce, tomato, and cheese");
        Item item2 = new Item(2L, "Fries", 3.99, "Crispy golden fries");
        Item item3 = new Item(3L, "Milkshake", 4.99, "Thick and creamy chocolate milkshake");
        Item item4 = new Item(4L, "Salad", 6.99, "Fresh garden salad with mixed greens and vegetables");
        Item item5 = new Item(5L, "Pizza", 12.99, "Delicious pepperoni pizza with cheese and tomato sauce");

        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);

        foodTrucks = new ArrayList<>();
        FoodTruck foodTruck1 = new FoodTruck(1L, "FoodTruck1", new User(1L, "user", "password", "OWNER", "TestCompany"), null);
        FoodTruck foodTruck2 = new FoodTruck(2L, "FoodTruck2", new User(1L, "user", "password", "OWNER", "TestCompany"), null);
        foodTrucks.add(foodTruck1);
        foodTrucks.add(foodTruck2);
    }
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    void createMenu(){
        Menu menu = new Menu(1L,items,foodTrucks );
        menuService.createMenu(menu);
        verify(menuRepository, times(1)).save(menu);
    }

    @Test
    void getAllMenus(){
        Menu menu1 = new Menu(1L,items,foodTrucks);
        Menu menu2 = new Menu(2L,items,foodTrucks);
        List<Menu> expectedMenus = Arrays.asList(menu1,menu2);

        when(menuRepository.findAll()).thenReturn(expectedMenus);
        List<Menu> actualMenus = menuService.getAllMenus();

        Assertions.assertEquals(expectedMenus,actualMenus);
    }
}
