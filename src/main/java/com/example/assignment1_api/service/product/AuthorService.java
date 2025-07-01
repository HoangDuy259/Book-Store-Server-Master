package com.example.assignment1_api.service.product;

import com.example.assignment1_api.dto.request.product.AuthorCreateRequest;
import com.example.assignment1_api.dto.response.product.AuthorResponse;
import com.example.assignment1_api.entity.product.Author;
import com.example.assignment1_api.mapper.AuthorMapper;
import com.example.assignment1_api.repository.AuthorRepository;
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
public class AuthorService {
    AuthorRepository authorRepository;
    AuthorMapper authorMapper;
    StringUtils stringUtils;

    public AuthorResponse createAuthor(AuthorCreateRequest request) {
        request.setName(stringUtils.getExactName(request.getName()));
        if(authorRepository.existsByName(request.getName()))
            throw new IllegalArgumentException("Author already exists");
        Author author = authorMapper.toAuthor(request);
        return authorMapper.toAuthorResponse(authorRepository.save(author));
    }
}
