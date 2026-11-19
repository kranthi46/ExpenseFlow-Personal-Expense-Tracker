package com.example.Springdemo.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Springdemo.model.Expense;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FileStorage {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String FILE_PATH = "data/expenses.json";

    public FileStorage() {
        createFile();
    }

    private void createFile() {

        try {
            File file = new File(FILE_PATH);

            if (!file.exists()) {

                file.getParentFile().mkdirs();

                objectMapper.writeValue(file, new ArrayList<Expense>());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Expense> getAllExpenses() {

        try {

            File file = new File(FILE_PATH);

            return objectMapper.readValue(
                file,
                new TypeReference<List<Expense>>() {}
            );

        } catch (IOException e) {

            e.printStackTrace();

            return new ArrayList<>();
        }
    }

    public void saveExpenses(List<Expense> expenses) {

        try {

            File file = new File(FILE_PATH);

            objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValue(file, expenses);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}