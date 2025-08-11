package com.example.login_service.utils;

import com.example.login_service.model.User;
import com.example.login_service.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;

    public OAuth2LoginSuccessHandler(JwtUtil jwtUtil, UserRepository userRepo) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        // If user logging in via Google doesn't exist in DB, register with default role
        User user = userRepo.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setRole("TRAVELER");
            userRepo.save(user);
        }

        // Generate and return your custom JWT (with RBAC)
        String jwtToken = jwtUtil.generateToken(email, user.getRole(), user.getUserId());

        // Redirect to frontend with token (or you can return in body)
//        System.out.println("JwtToken"+jwtToken);
//        response.sendRedirect("/token?jwt=" + jwtToken);
        //response.setContentType("application/json");
        //response.setCharacterEncoding("UTF-8");

        // Create a simple JSON response (you can use a DTO like AuthResponse)
//        String jsonResponse = String.format(
//                "{\"token\": \"%s\", \"role\": \"%s\", \"email\": \"%s\", \"name\": \"%s\"}",
//                jwtToken, user.getRole(), email, name
//        );

        // Write JSON to response
        response.sendRedirect("/token?jwt="+jwtToken);
    }
}
