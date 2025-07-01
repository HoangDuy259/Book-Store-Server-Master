package com.example.assignment1_api.mapper;

import com.example.assignment1_api.dto.request.UserRegisterRequest;
import com.example.assignment1_api.dto.response.user.UserResponse;
import com.example.assignment1_api.entity.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRegisterRequest request);

    UserResponse toUserResponse(User user);
}
