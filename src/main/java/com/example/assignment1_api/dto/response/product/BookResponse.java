package com.example.assignment1_api.dto.response.product;

import com.example.assignment1_api.entity.product.Author;
import com.example.assignment1_api.entity.product.Category;
import com.example.assignment1_api.entity.product.Publisher;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
     String title;
     String description;
     Integer price;
     Integer quantity;
     AuthorResponse author;
     PublisherResponse publisher;
     CategoryResponse category;
}
