package com.example.assignment1_api.controller;

import com.example.assignment1_api.dto.request.UserRegisterRequest;
import com.example.assignment1_api.utils.FeedBackMessage;
import com.example.assignment1_api.dto.identity.TokenExchangeResponse;
import com.example.assignment1_api.dto.response.user.UserResponse;
import com.example.assignment1_api.dto.request.LoginRequest;
import com.example.assignment1_api.dto.response.ApiResponse;
import com.example.assignment1_api.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
//import io.swagger.v3.oas.annotations.Operation;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        try {
            return ResponseEntity.status(CREATED)
                    .body(new ApiResponse<>(FeedBackMessage.CREATE_USER_SUCCESS,
                            userService.register(userRegisterRequest)));
        }catch (IllegalArgumentException  e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Lỗi đăng ký", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenExchangeResponse>> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            TokenExchangeResponse tokenResponse = userService.login(loginRequest);
            return ResponseEntity.ok(new ApiResponse<>(FeedBackMessage.LOGIN_SUCCESS, tokenResponse));
        } catch (AuthenticationException | DisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }
}
