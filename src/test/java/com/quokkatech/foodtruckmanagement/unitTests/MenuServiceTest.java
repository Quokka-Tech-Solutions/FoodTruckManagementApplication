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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MenuServiceTest {
    @Mock
    private MenuRepository menuRepository;
    @InjectMocks
    private MenuService menuService;

    private static List<Item> items;
    private static List<FoodTruck> foodTrucks;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

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

        assertEquals(expectedMenus,actualMenus);
    }

    @Test
    void getMenuByIdTest() {
        // Given
        Long menuId = 1L;
        Menu expectedMenu = new Menu(menuId, items, foodTrucks);

        // Mock the behavior of the MenuRepository
        when(menuRepository.findById(menuId)).thenReturn(java.util.Optional.of(expectedMenu));

        // When
        Optional<Menu> result = menuService.getMenuById(menuId);

        // Then
        assertEquals(expectedMenu, result.get());
    }

    @Test
    void getMenuByIdNotFoundTest() {
        // Given
        Long menuId = 1L;

        // Mock the behavior of the MenuRepository to return an empty Optional
        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        // When
        Optional<Menu> result = menuService.getMenuById(menuId);

        // Then
        assertThrows(NoSuchElementException.class, result::get);
    }
    @Test
    void updateMenuTest() {
        // Given
        Long menuId = 1L;
        Menu existingMenu = new Menu(menuId, items, foodTrucks);
        Menu updatedMenu = new Menu(menuId, new ArrayList<>(items), new ArrayList<>(foodTrucks));

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(existingMenu));

        // When
        menuService.updateMenu(menuId, updatedMenu);

        // Then
        verify(menuRepository, times(1)).save(updatedMenu);
    }

    @Test
    void updateMenuNotFoundTest() {
        // Given
        Long menuId = 1L;
        Menu updatedMenu = new Menu(menuId, items, foodTrucks);

        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> menuService.updateMenu(menuId, updatedMenu));
    }

    @Test
    void addItemTest() {
        // Given
        Long menuId = 1L;
        Menu existingMenu = new Menu(menuId, items, foodTrucks);
        Item newItem = new Item(6L, "New Item", 9.99, "A new menu item");

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(existingMenu));

        // When
        menuService.addItem(menuId, newItem);

        // Then
        verify(menuRepository, times(1)).save(existingMenu);
        assertEquals(6, existingMenu.getItems().size());
        assertEquals(newItem, existingMenu.getItems().get(5));
    }

    @Test
    void addItemNotFoundTest() {
        // Given
        Long menuId = 1L;
        Item newItem = new Item(6L, "New Item", 9.99, "A new menu item");

        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> menuService.addItem(menuId, newItem));
    }

    @Test
    void removeItemTest() {
        // Given
        Long menuId = 1L;
        Menu existingMenu = new Menu(menuId, items, foodTrucks);
        Item itemToRemove = items.get(0);

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(existingMenu));

        // When
        menuService.removeItem(menuId, itemToRemove);

        // Then
        verify(menuRepository, times(1)).save(existingMenu);
        assertEquals(4, existingMenu.getItems().size());
        assertFalse(existingMenu.getItems().contains(itemToRemove));
    }

    @Test
    void removeItemNotFoundTest() {
        // Given
        Long menuId = 1L;
        Item itemToRemove = new Item(6L, "New Item", 9.99, "A new menu item");

        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> menuService.removeItem(menuId, itemToRemove));
    }

}
