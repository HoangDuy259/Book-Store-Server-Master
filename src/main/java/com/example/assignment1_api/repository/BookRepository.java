package com.example.assignment1_api.repository;

import com.example.assignment1_api.dto.response.product.BookResponse;
import com.example.assignment1_api.entity.product.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByName(String name);

    Book findBookByName(String name);

    BookResponse findBookById(Long id);

    Optional<Book> findByName(String name);
}
