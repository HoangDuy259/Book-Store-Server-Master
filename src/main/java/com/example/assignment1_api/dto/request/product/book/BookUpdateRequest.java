package com.example.assignment1_api.dto.request.product.book;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookUpdateRequest {
    String title;
    String description;
    Integer price;
    Integer quantity;
    String author;
    String publisher;
    String category;
}
