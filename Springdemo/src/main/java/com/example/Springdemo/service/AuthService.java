
package com.example.Springdemo.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Service;

import com.example.Springdemo.model.User;
import com.example.Springdemo.storage.UserStorage;

@Service
public class AuthService {

    private final UserStorage userStorage;

    public AuthService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    // Register a new user
    public User signup(String name, String email, String password) {

        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("Name is required");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        if (password == null || password.length() < 8) {
            throw new RuntimeException("Password must contain at least 8 characters");
        }

        email = email.trim().toLowerCase();

        if (userStorage.findByEmail(email) != null) {
            throw new RuntimeException("Email is already registered");
        }

        Long id = userStorage.findAll().stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0) + 1;

        String passwordHash = hashPassword(password);

        User user = new User(id, name.trim(), email, passwordHash);

        userStorage.save(user);

        return user;
    }

    // Authenticate an existing user
    public User login(String email, String password) {

        if (email == null || password == null) {
            throw new RuntimeException("Email and password are required");
        }

        User user = userStorage.findByEmail(email.trim().toLowerCase());

        if (user == null || !verifyPassword(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }

    // Generate a secure PBKDF2 password hash
    private String hashPassword(String password) {

        try {
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);

            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    120000,
                    256
            );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] hash = factory.generateSecret(spec).getEncoded();
            spec.clearPassword();

            return Base64.getEncoder().encodeToString(salt)
                    + ":" +
                    Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }

    // Verify the entered password against the stored hash
    private boolean verifyPassword(String password, String storedHash) {

        try {
            String[] parts = storedHash.split(":");

            if (parts.length != 2) {
                return false;
            }

            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] expectedHash = Base64.getDecoder().decode(parts[1]);

            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    120000,
                    256
            );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] actualHash = factory.generateSecret(spec).getEncoded();
            spec.clearPassword();

            if (actualHash.length != expectedHash.length) {
                return false;
            }

            int difference = 0;

            for (int i = 0; i < actualHash.length; i++) {
                difference |= actualHash[i] ^ expectedHash[i];
            }

            return difference == 0;

        } catch (Exception e) {
            return false;
        }
    }
}
