package com.quokkatech.foodtruckmanagement.application.services;

import com.quokkatech.foodtruckmanagement.domain.entities.Menu;
import com.quokkatech.foodtruckmanagement.domain.repositories.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    private MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void createMenu(Menu menu){
        menuRepository.save(menu);
    }

    public List<Menu> getAllMenus(){
        return menuRepository.findAll();
    }
}
