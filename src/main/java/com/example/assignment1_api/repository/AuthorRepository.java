package com.example.assignment1_api.repository;

import com.example.assignment1_api.entity.product.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, String> {
    boolean existsByName(String name);

    Author findByName(String name);
}
