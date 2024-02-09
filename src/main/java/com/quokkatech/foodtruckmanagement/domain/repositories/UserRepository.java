package com.quokkatech.foodtruckmanagement.domain.repositories;

import com.quokkatech.foodtruckmanagement.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUsername(String username);
}