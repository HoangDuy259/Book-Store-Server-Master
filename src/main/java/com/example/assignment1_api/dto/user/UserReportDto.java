package com.example.assignment1_api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserReportDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
}