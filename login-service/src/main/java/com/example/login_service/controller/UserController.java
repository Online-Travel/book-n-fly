package com.example.login_service.controller;

import com.example.login_service.DTO.AuthResponse;
import com.example.login_service.DTO.LoginRequest;
import com.example.login_service.model.User;
import com.example.login_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Register a new user with validation
    @PostMapping("/adduser")
    public ResponseEntity<String> addUsers(@RequestBody User user){
        return userService.register(user);
        empList.stream().filter(name -> name.substring(0,1).equals("a" || "b" || "c")).Collectors.collect
    }

    // Login existing user and return JWT token
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

}
