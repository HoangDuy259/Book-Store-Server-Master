package com.example.assignment1_api.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
