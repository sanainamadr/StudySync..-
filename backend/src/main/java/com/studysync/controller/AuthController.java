package com.studysync.controller;

import com.studysync.model.User;
import com.studysync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ---- REGISTER ----
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();

        String email = body.get("email");
        String name = body.get("name");
        String password = body.get("password");

        if (userRepository.existsByEmail(email)) {
            response.put("success", false);
            response.put("message", "Email already registered");
            return ResponseEntity.badRequest().body(response);
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // hash password!
        userRepository.save(user);

        response.put("success", true);
        response.put("message", "Registration successful");
        response.put("userId", user.getId());
        response.put("name", user.getName());
        return ResponseEntity.ok(response);
    }

    // ---- LOGIN ----
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();

        String email = body.get("email");
        String password = body.get("password");

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "Email not found");
            return ResponseEntity.badRequest().body(response);
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            response.put("success", false);
            response.put("message", "Incorrect password");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("success", true);
        response.put("message", "Login successful");
        response.put("userId", user.getId());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("streakCount", user.getStreakCount());
        return ResponseEntity.ok(response);
    }

    // ---- GET USER PROFILE ----
    @GetMapping("/user/{id}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "User not found");
            return ResponseEntity.badRequest().body(response);
        }

        User user = userOpt.get();
        response.put("success", true);
        response.put("userId", user.getId());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("streakCount", user.getStreakCount());
        response.put("createdAt", user.getCreatedAt());
        return ResponseEntity.ok(response);
    }
}
