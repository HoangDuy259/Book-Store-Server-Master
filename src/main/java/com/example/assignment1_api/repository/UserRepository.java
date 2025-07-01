package com.example.assignment1_api.repository;

import com.example.assignment1_api.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByUserId();

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findUserByEmail(String email);

    User findUserByUsername(String username);

}
