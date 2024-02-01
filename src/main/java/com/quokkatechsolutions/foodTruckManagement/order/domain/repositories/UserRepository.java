package com.quokkatechsolutions.foodTruckManagement.order.domain.repositories;

import com.quokkatechsolutions.foodTruckManagement.order.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
