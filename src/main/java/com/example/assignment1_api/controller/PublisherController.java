package com.example.assignment1_api.controller;

import com.example.assignment1_api.dto.request.product.PublisherCreateRequest;
import com.example.assignment1_api.dto.response.ApiResponse;
import com.example.assignment1_api.dto.response.product.PublisherResponse;
import com.example.assignment1_api.service.product.PublisherService;
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
@RequestMapping("/api/publisher")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PublisherController {
    PublisherService publisherService;
    @PostMapping
    public ResponseEntity<ApiResponse<PublisherResponse>> createPublisher(@RequestBody PublisherCreateRequest request) {
        try {
            return ResponseEntity.status(CREATED)
                    .body(new ApiResponse<>(FeedBackMessage.SUCCESS, publisherService.createPublisher(request)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }
}
