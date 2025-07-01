package com.example.assignment1_api.mapper;

import com.example.assignment1_api.dto.request.product.CategoryCreateRequest;
import com.example.assignment1_api.dto.response.product.CategoryResponse;
import com.example.assignment1_api.entity.product.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryCreateRequest request);

    CategoryResponse toCategoryResponse(Category category);
}
