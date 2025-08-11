package com.example.login_service.service;

import com.example.login_service.DTO.AuthResponse;
import com.example.login_service.DTO.EmailDtoResponse;
import com.example.login_service.DTO.LoginRequest;
import com.example.login_service.DTO.Validation;
import com.example.login_service.model.User;
import com.example.login_service.repository.UserRepository;
import com.example.login_service.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private Validation validate;

    // Register a new user with role and password validation
    public ResponseEntity<String> register(User user) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        if (!validate.isValidNumber(user.getContactNumber())) {
            return ResponseEntity.badRequest().body("Invalid contact number");
        }

        if (!validate.isValidPassword(user.getPassword())) {
            return ResponseEntity.badRequest().body(
                    "Invalid password. Password must have at least 8 characters, including uppercase, lowercase, digit, and special character.");
        }

        if (!validate.isValidRole(user.getRole())) {
            return ResponseEntity.badRequest()
                    .body("Invalid role. Allowed roles: Traveler, Hotel manager, Travel agent");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole().toUpperCase().replace(" ", "_"));
        userRepo.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    // Get all registered users
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // Get a user by ID
    public Optional<User> getUserById(int id) {
        return userRepo.findById(id);
    }

    // Delete user by ID
    public ResponseEntity<String> deleteUser(int id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return ResponseEntity.ok("User deleted");
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    // Update a user's role
    public ResponseEntity<String> updateRole(int id, String newRole) {
        Optional<User> optUser = userRepo.findById(id);
        if (optUser.isPresent()) {
            User user = optUser.get();
            user.setRole(newRole.toUpperCase());
            userRepo.save(user);
            return ResponseEntity.ok("Role updated successfully");
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    // Authenticate user and return JWT token
    public ResponseEntity<AuthResponse> login(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail());
        //System.out.println(user);
        //System.out.println(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()));
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getUserId());
        AuthResponse authResponse = new AuthResponse(token, user.getRole());

        return ResponseEntity.ok(authResponse);
    }

    public ResponseEntity<EmailDtoResponse> getEmailById(int id) {
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()) {
            User u = user.get();
            EmailDtoResponse email = new EmailDtoResponse(u.getEmail());
            return new ResponseEntity<EmailDtoResponse>(email, HttpStatus.OK);
        }
        return new ResponseEntity<EmailDtoResponse>(HttpStatus.NOT_FOUND);
    }
}
