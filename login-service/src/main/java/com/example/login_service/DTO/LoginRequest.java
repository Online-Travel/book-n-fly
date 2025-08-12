package com.example.login_service.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
