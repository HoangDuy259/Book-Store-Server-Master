package com.example.assignment1_api.service.product;

import com.example.assignment1_api.dto.request.product.CategoryCreateRequest;
import com.example.assignment1_api.dto.response.product.CategoryResponse;
import com.example.assignment1_api.entity.product.Category;
import com.example.assignment1_api.mapper.CategoryMapper;
import com.example.assignment1_api.repository.CategoryRepository;
import com.example.assignment1_api.utils.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    StringUtils stringUtils;

    public CategoryResponse createCategory(CategoryCreateRequest request) {
        request.setName(stringUtils.getExactName(request.getName()));

        if(categoryRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Category name already exists");
        }
        Category category = categoryMapper.toCategory(request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }
}
