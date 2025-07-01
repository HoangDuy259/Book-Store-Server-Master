package com.example.assignment1_api.controller;

import com.example.assignment1_api.dto.request.product.AuthorCreateRequest;
import com.example.assignment1_api.dto.response.ApiResponse;
import com.example.assignment1_api.dto.response.product.AuthorResponse;
import com.example.assignment1_api.service.product.AuthorService;
import com.example.assignment1_api.utils.FeedBackMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/author")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorController {
    AuthorService authorService;
    @PostMapping
    public ResponseEntity<ApiResponse<AuthorResponse>> createAuthor(@RequestBody AuthorCreateRequest request) {
        try {
            return ResponseEntity.status(CREATED)
                    .body(new ApiResponse<>(FeedBackMessage.SUCCESS, authorService.createAuthor(request)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }
}
