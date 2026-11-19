
package com.example.Springdemo.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Springdemo.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserStorage {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final File file = new File(
        System.getProperty("user.home"),
        "expense-tracker-data/users.json"
    );

    public UserStorage() {
        File directory = file.getParentFile();

        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
                objectMapper.writeValue(file, new ArrayList<User>());
            } catch (IOException e) {
                throw new RuntimeException("Unable to create users.json", e);
            }
        }
    }

    // Read all registered users
    public synchronized List<User> findAll() {
        try {
            if (file.length() == 0) {
                return new ArrayList<>();
            }

            return objectMapper.readValue(
                file,
                new TypeReference<List<User>>() {}
            );

        } catch (IOException e) {
            throw new RuntimeException("Unable to read users.json", e);
        }
    }

    // Find user by email
    public synchronized User findByEmail(String email) {
        return findAll().stream()
            .filter(user -> user.getEmail() != null
                && user.getEmail().equalsIgnoreCase(email))
            .findFirst()
            .orElse(null);
    }

    // Save a new user
    public synchronized void save(User user) {
        List<User> users = findAll();

        users.add(user);

        try {
            objectMapper.writeValue(file, users);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save user", e);
        }
    }
}
