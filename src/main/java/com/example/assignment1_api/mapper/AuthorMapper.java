package com.example.assignment1_api.mapper;


import com.example.assignment1_api.dto.request.product.AuthorCreateRequest;
import com.example.assignment1_api.dto.response.product.AuthorResponse;
import com.example.assignment1_api.entity.product.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toAuthor(AuthorCreateRequest request);

    AuthorResponse toAuthorResponse(Author author);
}
