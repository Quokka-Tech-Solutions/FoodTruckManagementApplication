package com.quokkatech.foodtruckmanagement.api.controllers;

import com.quokkatech.foodtruckmanagement.api.request.MenuRequest;
import com.quokkatech.foodtruckmanagement.api.response.FoodTruckResponse;
import com.quokkatech.foodtruckmanagement.api.response.MenuResponse;
import com.quokkatech.foodtruckmanagement.application.services.MenuService;
import com.quokkatech.foodtruckmanagement.domain.entities.Menu;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/menus")
public class MenuController {
    private final MenuService menuService;
    private static final Logger logger = LoggerFactory.getLogger(UserSessionController.class);

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("")
    public ResponseEntity<MenuResponse> createMenu(@RequestBody MenuRequest menuRequest){
        try{
            ModelMapper modelMapper = new ModelMapper();
            Menu menu = modelMapper.map(menuRequest, Menu.class);
            menuService.createMenu(menu);

            MenuResponse menuResponse = new MenuResponse(menuRequest.getMenuId(),menuRequest.getItems(),menuRequest.getFoodTrucks());
            logger.info("Created menu with ID:" + menuRequest.getMenuId());
            return ResponseEntity.status(HttpStatus.CREATED).body(menuResponse);
        }
        catch (Exception e){
            logger.info("Unable to create menu with ID:" + menuRequest.getMenuId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MenuResponse(menuRequest.getMenuId(),menuRequest.getItems(),menuRequest.getFoodTrucks()));
        }
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponse> getMenuById(@PathVariable Long menuId){
        logger.info("Getting menu with ID:" + menuId);
        try{
            Optional<Menu> menuOptional = menuService.getMenuById(menuId);
            if (menuOptional.isPresent()){
                Menu menu = menuOptional.get();
                MenuResponse menuResponse = new MenuResponse(menu.getMenuId(), menu.getItems(),menu.getFoodTrucks());
                return ResponseEntity.ok(menuResponse);
            }
            else {
                return ResponseEntity.notFound().build();
            }

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
