package com.quokkatech.foodtruckmanagement.domain.repositories;

import com.quokkatech.foodtruckmanagement.domain.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
}
