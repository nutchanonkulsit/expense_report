package com.spring.expense_report.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.expense_report.entity.Role;
import com.spring.expense_report.entity.User;
import com.spring.expense_report.repository.RoleRepository;
import com.spring.expense_report.repository.UserRepository;
import com.spring.expense_report.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @SuppressWarnings("CallToPrintStackTrace")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");
            String name = request.get("name");
            String role = request.get("role");

            if (userRepository.findByEmail(email).isPresent()) {
                return ResponseEntity.status(400).body(Map.of(
                        "status", 400,
                        "error", "Bad Request",
                        "message", "Email already in use"));
            }

            String hashedPassword = passwordEncoder.encode(password);

            User user = new User();
            Role userRole = new Role();

            user.setEmail(email);
            user.setPassword(hashedPassword);
            user.setName(name);

            // Fetch all roles and find the matching one
            List<Role> availableRoles = roleRepository.findAll();

            Role matchedRole = availableRoles.stream()
                    .filter(r -> r.getName().equalsIgnoreCase(role))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Role not found: " + role));

            userRole.setId(matchedRole.getId());

            user.setRole(userRole);

            userRepository.save(user);

            return ResponseEntity.status(201).body(Map.of(
                    "status", 201,
                    "message", "User registered successfully",
                    "user", Map.of("email", user.getEmail(), "name", user.getName())));

        } catch (Exception e) {
            // Log the exception if needed
            e.printStackTrace();

            return ResponseEntity.status(500).body(Map.of(
                    "status", 500,
                    "error", "Internal Server Error",
                    "message", e.getMessage()));
        }
    }

    @PostMapping("/login")
    @SuppressWarnings("CallToPrintStackTrace")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {

            String email = request.get("email");
            String password = request.get("password");

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                return ResponseEntity.status(401).body(Map.of(
                        "status", 401,
                        "error", "Unauthorized",
                        "message", "Invalid credentials"));
            }

            String token = jwtUtil.generateToken(email);
            String refreshToken = jwtUtil.generateRefreshToken(email);

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "refreshToken", refreshToken,
                    "tokenType", "Bearer",
                    "email", user.getEmail(),
                    "name", user.getName()));

        } catch (RuntimeException e) {
            // For things like "User not found"
            return ResponseEntity.status(404).body(Map.of(
                    "status", 404,
                    "error", "Not Found",
                    "message", e.getMessage()));

        } catch (Exception e) {
            // For any other unexpected errors
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "status", 500,
                    "error", "Internal Server Error",
                    "message", e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> req) {
        String refreshToken = req.get("refreshToken");

        try {
            Claims claims = jwtUtil.parseToken(refreshToken);
            String username = claims.getSubject();

            String newAccessToken = jwtUtil.generateToken(username);

            return ResponseEntity.ok(Map.of(
                    "accessToken", newAccessToken));
        } catch (JwtException e) {
            return ResponseEntity.status(401).body(Map.of(
                    "error", "TOKEN_EXPIRED"));
        }
    }

    // @PostMapping("/validate-token")
    // public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
    //     String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;

    //     boolean isValid = jwtUtil.validateToken(token);

    //     if (isValid) {
    //         return ResponseEntity.ok().body("Token is valid");
    //     } else {
    //         return ResponseEntity.status(401).body("Invalid or expired token");
    //     }
    // }

    @PostMapping("/validate-token")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;

        boolean isValid = jwtUtil.validateToken(token);

        Map<String, Object> body = new HashMap<>();
        body.put("valid", isValid);

        if (isValid) {
            return ResponseEntity.ok(body);
        } else {
            return ResponseEntity.status(401).body(body);
        }
    }

}
