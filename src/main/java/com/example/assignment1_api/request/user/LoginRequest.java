package com.example.assignment1_api.request.user;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
