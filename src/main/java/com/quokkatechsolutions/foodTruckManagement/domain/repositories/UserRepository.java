package com.quokkatechsolutions.foodTruckManagement.domain.repositories;

import com.quokkatechsolutions.foodTruckManagement.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
