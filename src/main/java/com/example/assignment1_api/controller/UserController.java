package com.example.assignment1_api.controller;


import com.example.assignment1_api.dto.response.ApiResponse;
import com.example.assignment1_api.dto.response.user.UserResponse;
import com.example.assignment1_api.service.UserService;
import com.example.assignment1_api.utils.FeedBackMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
       return ResponseEntity.status(HttpStatus.OK)
               .body(new ApiResponse<>(FeedBackMessage.USER_FOUND, userService.getAllUsers()));
    }
}
