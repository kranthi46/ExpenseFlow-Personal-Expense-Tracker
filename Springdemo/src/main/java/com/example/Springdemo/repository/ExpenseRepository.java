
package com.example.Springdemo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.Springdemo.model.Expense;
import com.example.Springdemo.storage.FileStorage;

@Repository
public class ExpenseRepository {

    private final FileStorage fileStorage;

    public ExpenseRepository(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    // Get all transactions
    public List<Expense> findAll() {
        return fileStorage.getAllExpenses();
    }

    // Get transactions belonging to a particular user
    public List<Expense> findAllByUserId(Long userId) {

        List<Expense> result = new ArrayList<>();

        for (Expense expense : fileStorage.getAllExpenses()) {

            if (userId.equals(expense.getUserId())) {
                result.add(expense);
            }
        }

        return result;
    }

    // Find transaction by ID
    public Expense findById(Long id) {

        for (Expense expense : fileStorage.getAllExpenses()) {

            if (expense.getId().equals(id)) {
                return expense;
            }
        }

        return null;
    }

    // Find transaction by ID and user ID
    public Expense findByIdAndUserId(Long id, Long userId) {

        for (Expense expense : fileStorage.getAllExpenses()) {

            if (expense.getId().equals(id)
                    && userId.equals(expense.getUserId())) {

                return expense;
            }
        }

        return null;
    }

    // Save a new transaction
    public Expense save(Expense expense) {

        List<Expense> expenses = fileStorage.getAllExpenses();

        long nextId = 1;

        for (Expense existing : expenses) {

            if (existing.getId() != null
                    && existing.getId() >= nextId) {

                nextId = existing.getId() + 1;
            }
        }

        expense.setId(nextId);

        expenses.add(expense);

        fileStorage.saveExpenses(expenses);

        return expense;
    }

    // Update transaction
    public Expense update(Long id, Expense updatedExpense) {

        List<Expense> expenses = fileStorage.getAllExpenses();

        for (int i = 0; i < expenses.size(); i++) {

            Expense existing = expenses.get(i);

            if (existing.getId().equals(id)) {

                updatedExpense.setId(id);

                expenses.set(i, updatedExpense);

                fileStorage.saveExpenses(expenses);

                return updatedExpense;
            }
        }

        return null;
    }

    // Delete transaction by ID
    public boolean deleteById(Long id) {

        List<Expense> expenses = fileStorage.getAllExpenses();

        boolean removed = expenses.removeIf(
                expense -> expense.getId().equals(id)
        );

        if (removed) {
            fileStorage.saveExpenses(expenses);
        }

        return removed;
    }

    // Delete transaction only if it belongs to the user
    public boolean deleteByIdAndUserId(Long id, Long userId) {

        List<Expense> expenses = fileStorage.getAllExpenses();

        boolean removed = expenses.removeIf(
                expense -> expense.getId().equals(id)
                        && userId.equals(expense.getUserId())
        );

        if (removed) {
            fileStorage.saveExpenses(expenses);
        }

        return removed;
    }
}
