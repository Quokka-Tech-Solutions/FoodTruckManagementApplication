package com.quokkatech.foodtruckmanagement.application.services;

import com.quokkatech.foodtruckmanagement.domain.entities.Item;
import com.quokkatech.foodtruckmanagement.domain.entities.Menu;
import com.quokkatech.foodtruckmanagement.domain.repositories.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MenuService {
    private MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void createMenu(Menu menu){
        menuRepository.save(menu);
    }

    public Optional<Menu> getMenuById(Long menuId){
        return menuRepository.findById(menuId);
    }
    public List<Menu> getAllMenus(){
        return menuRepository.findAll();
    }
    public void updateMenu(Long menuId, Menu updatedMenu) {
        Menu menuToUpdate = menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException("Menu with ID " + menuId + " not found"));

        menuToUpdate.setMenuId(updatedMenu.getMenuId());
        menuToUpdate.setItems(updatedMenu.getItems());
        menuToUpdate.setFoodTrucks(updatedMenu.getFoodTrucks());
        menuRepository.save(menuToUpdate);
    }

    public void addItem(Long menuId, Item item){
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found"));
        menu.getItems().add(item);
        menuRepository.save(menu);
    }

    public void removeItem(Long menuId, Item item){
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found"));
        menu.getItems().remove(item);
        menuRepository.save(menu);
    }
}
