package com.quokkatech.foodtruckmanagement.integrationTests;

import com.quokkatech.foodtruckmanagement.api.controllers.MenuController;
import com.quokkatech.foodtruckmanagement.api.request.MenuRequest;
import com.quokkatech.foodtruckmanagement.api.response.MenuResponse;
import com.quokkatech.foodtruckmanagement.application.services.MenuService;
import com.quokkatech.foodtruckmanagement.domain.entities.FoodTruck;
import com.quokkatech.foodtruckmanagement.domain.entities.Item;
import com.quokkatech.foodtruckmanagement.domain.entities.Menu;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import com.quokkatech.foodtruckmanagement.domain.repositories.MenuRepository;
import com.quokkatech.foodtruckmanagement.integrationTests.config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestConfig.class)
public class MenuControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MenuController menuController;
    @MockBean
    private MenuRepository menuRepository;
    @MockBean
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
    void createMenuShouldReturnCreatedStatus(){
        MenuRequest menuRequest = new MenuRequest(1L,items,foodTrucks);

        ResponseEntity<MenuResponse> response = restTemplate.postForEntity("/menus", menuRequest, MenuResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMenuId()).isEqualTo(1L);
    }

    @Test
    void getMenuByIdShouldReturnMenuById(){
        Menu mockMenu = new Menu(1L, items, foodTrucks);

        when(menuService.getMenuById(1L)).thenReturn(Optional.of(mockMenu));

        ResponseEntity<MenuResponse> response = restTemplate.getForEntity("/menus/1", MenuResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMenuId()).isEqualTo(1L);
    }
}
