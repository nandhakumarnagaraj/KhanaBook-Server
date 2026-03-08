package com.khanabook.saas.controller;

import com.khanabook.saas.utility.JwtUtility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtility jwtUtility;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // In a real app, verify password here. For sync test, we generate a real JWT.
        String token = jwtUtility.generateToken(request.getEmail(), 1L);
        return ResponseEntity.ok(new AuthResponse(token, 1L, "Admin User", "admin"));
    }

    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody GoogleLoginRequest request) {
        // For sync test, generate real JWT from Google ID token placeholder
        String token = jwtUtility.generateToken("google_user", 1L);
        return ResponseEntity.ok(new AuthResponse(token, 1L, "Google User", "admin"));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {
        private String email;
        private String passwordHash;
        private String deviceId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GoogleLoginRequest {
        private String idToken;
        private String deviceId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthResponse {
        private String token;
        private Long restaurantId;
        private String userName;
        private String role;
    }
}
