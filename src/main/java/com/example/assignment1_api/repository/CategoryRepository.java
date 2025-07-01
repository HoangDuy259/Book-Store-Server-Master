package com.example.assignment1_api.repository;

import com.example.assignment1_api.entity.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
    boolean existsByName(String name);

    Category findByName(String name);
}
