package com.quokkatech.foodtruckmanagement.domain.repositories;

import com.quokkatech.foodtruckmanagement.domain.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile,Long> {

}
