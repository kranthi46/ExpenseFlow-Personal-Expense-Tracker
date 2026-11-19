
package com.example.Springdemo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Springdemo.model.User;
import com.example.Springdemo.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(
    origins = "http://localhost:5173",
    allowCredentials = "true"
)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Signup API
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(
            @RequestBody Map<String, String> request,
            HttpServletRequest httpRequest) {

        try {
            User user = authService.signup(
                    request.get("name"),
                    request.get("email"),
                    request.get("password")
            );

            HttpSession session = httpRequest.getSession(true);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("userEmail", user.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Registration successful");
            response.put("name", user.getName());
            response.put("email", user.getEmail());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());

            return ResponseEntity.badRequest().body(response);
        }
    }

    // Login API
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody Map<String, String> request,
            HttpServletRequest httpRequest) {

        try {
            User user = authService.login(
                    request.get("email"),
                    request.get("password")
            );

            HttpSession session = httpRequest.getSession(true);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("userEmail", user.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("name", user.getName());
            response.put("email", user.getEmail());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }
    }

    // Check current logged-in user
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(
            HttpSession session) {

        Object userId = session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", userId);
        response.put("name", session.getAttribute("userName"));
        response.put("email", session.getAttribute("userEmail"));

        return ResponseEntity.ok(response);
    }

    // Logout API
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
            HttpSession session) {

        session.invalidate();

        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout successful");

        return ResponseEntity.ok(response);
    }
}
