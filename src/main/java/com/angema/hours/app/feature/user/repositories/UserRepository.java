package com.angema.hours.app.feature.user.repositories;

import com.angema.hours.app.feature.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
