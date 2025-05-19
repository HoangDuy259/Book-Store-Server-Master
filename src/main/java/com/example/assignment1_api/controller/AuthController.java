package com.example.assignment1_api.controller;

import com.example.assignment1_api.Utils.FeedBackMessage;
import com.example.assignment1_api.dto.EntityConverter;
import com.example.assignment1_api.dto.identity.TokenExchangeResponse;
import com.example.assignment1_api.dto.user.UserDto;
import com.example.assignment1_api.entity.user.User;
import com.example.assignment1_api.repository.UserRepository;
import com.example.assignment1_api.request.RegisterUserModel;
import com.example.assignment1_api.request.user.LoginRequest;
import com.example.assignment1_api.response.ApiResponse;
import com.example.assignment1_api.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
//import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final EntityConverter<User, UserDto> entityConverter;
    private final UserRepository userRepository;

//    @Operation(summary = "API đăng kí tài khoản mới")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(
            @RequestBody @Valid RegisterUserModel registerUserModel) {
        try {
            // Thực hiện đăng ký người dùng
            User userRegister = userService.register(registerUserModel);
            UserDto registeredUser = entityConverter.mapEntityToDto(userRegister, UserDto.class);
            // Trả về ApiResponse với thông báo thành công
            return ResponseEntity.status(CREATED)
                    .body(new ApiResponse<>(FeedBackMessage.CREATE_USER_SUCCESS, registeredUser));
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
