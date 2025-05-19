package com.example.assignment1_api.service.user;


import com.example.assignment1_api.dto.identity.TokenExchangeResponse;
import com.example.assignment1_api.dto.user.UserDto;
import com.example.assignment1_api.entity.user.User;
import com.example.assignment1_api.request.RegisterUserModel;
import com.example.assignment1_api.request.user.LoginRequest;

import javax.security.sasl.AuthenticationException;
import java.util.List;

public interface IUserService {

    User register(RegisterUserModel registerUserModel);

    TokenExchangeResponse login(LoginRequest loginRequest) throws AuthenticationException;

    List<UserDto> getAllUsers();

    UserDto getUserById();

    Boolean deleteUser(Long userId);

    boolean isUserEnabled(String username);

}
