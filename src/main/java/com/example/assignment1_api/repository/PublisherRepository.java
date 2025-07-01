package com.example.assignment1_api.repository;

import com.example.assignment1_api.entity.product.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, String> {
    boolean existsByName(String name);

    Publisher findByName(String name);
}
