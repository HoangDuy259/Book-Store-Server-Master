package com.example.assignment1_api.mapper;

import com.example.assignment1_api.dto.request.product.book.BookCreateRequest;
import com.example.assignment1_api.dto.request.product.book.BookUpdateRequest;
import com.example.assignment1_api.dto.response.product.BookResponse;
import com.example.assignment1_api.entity.product.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "name", ignore = true)
    Book toBook(BookCreateRequest request);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    void updateBook (@MappingTarget Book book, BookUpdateRequest request);

    BookResponse toBookResponse(Book book);


}
