package com.example.assignment1_api.service;

import com.example.assignment1_api.dto.identity.ClientTokenExchangeParam;
import com.example.assignment1_api.dto.identity.KeycloakProvider;
import com.example.assignment1_api.dto.identity.TokenExchangeResponse;
import com.example.assignment1_api.dto.request.UserRegisterRequest;
import com.example.assignment1_api.dto.response.user.UserResponse;
import com.example.assignment1_api.entity.user.User;
import com.example.assignment1_api.mapper.UserMapper;
import com.example.assignment1_api.repository.UserRepository;
import com.example.assignment1_api.dto.request.LoginRequest;
import com.example.assignment1_api.dto.response.keycloak.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService{
    UserMapper userMapper;
    UserRepository userRepository;
    KeycloakProvider keycloakProvider;
    IdentityClient identityClient;
    PasswordEncoder passwordEncoder;

    public UserResponse register(UserRegisterRequest userRegisterRequest) {
        if (userRepository.existsByUsername(userRegisterRequest.getUsername())) {
            throw new IllegalArgumentException("Username đã tồn tại");
        }

        if (userRepository.existsByEmail(userRegisterRequest.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        log.info("in a method");

        User user = userMapper.toUser(userRegisterRequest);
        log.info("user: " + user.getUsername());
        log.info("email: " + user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("password: " + user.getPassword());
        user.setEnabled(true);
        log.info("register successed: " + userRepository.count());
        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }


    public TokenExchangeResponse login(LoginRequest loginRequest) throws AuthenticationException {
        User user = userRepository.findUserByUsername(loginRequest.getUsername());

        // Kiểm tra nếu user không tồn tại
        if (user == null) {
            throw new AuthenticationException("Sai tên đăng nhập hoặc mật khẩu.");
        }

        // Kiểm tra nếu tài khoản bị vô hiệu hóa
        if (!user.getEnabled()) {
            throw new DisabledException("Tài khoản chưa được kích hoạt.");
        }

        // Gửi request đến Keycloak để lấy token
        return identityClient.exchangeTokenClient(ClientTokenExchangeParam.builder()
                .grant_type("password")
                .client_id(keycloakProvider.getClientID())
                .client_secret(keycloakProvider.getClientSecret())
                .username(loginRequest.getUsername())
                .password(loginRequest.getPassword())
                .scope("openid")
                .build());
    }


    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).collect(Collectors.toList());
    }

}
