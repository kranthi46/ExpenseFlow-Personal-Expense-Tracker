
package com.example.Springdemo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.Springdemo.model.Expense;
import com.example.Springdemo.repository.ExpenseRepository;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // Get transactions belonging to a specific user
    public List<Expense> getAllExpenses(Long userId) {
        return expenseRepository.findAllByUserId(userId);
    }

    // Get transaction by ID and user ID
    public Expense getExpenseById(Long id, Long userId) {

        Expense expense =
                expenseRepository.findByIdAndUserId(id, userId);

        if (expense == null) {
            throw new RuntimeException("Transaction not found");
        }

        return expense;
    }

    // Add transaction for a specific user
    public Expense addExpense(Expense expense, Long userId) {

        validateExpense(expense);

        // Always assign ownership on the server
        expense.setUserId(userId);

        return expenseRepository.save(expense);
    }

    // Update only the owner's transaction
    public Expense updateExpense(
            Long id, Expense updatedExpense, Long userId) {

        validateExpense(updatedExpense);

        Expense existingExpense =
                expenseRepository.findByIdAndUserId(id, userId);

        if (existingExpense == null) {
            throw new RuntimeException("Transaction not found");
        }

        updatedExpense.setId(id);
        updatedExpense.setUserId(userId);

        return expenseRepository.update(id, updatedExpense);
    }

    // Delete only the owner's transaction
    public void deleteExpense(Long id, Long userId) {

        boolean deleted =
                expenseRepository.deleteByIdAndUserId(id, userId);

        if (!deleted) {
            throw new RuntimeException("Transaction not found");
        }
    }

    // Validate transaction
    private void validateExpense(Expense expense) {

        if (expense.getTitle() == null ||
                expense.getTitle().trim().isEmpty()) {

            throw new IllegalArgumentException("Title is required");
        }

        if (expense.getAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero");
        }

        if (expense.getType() == null ||
                (!expense.getType().equalsIgnoreCase("INCOME") &&
                 !expense.getType().equalsIgnoreCase("EXPENSE"))) {

            throw new IllegalArgumentException(
                    "Type must be INCOME or EXPENSE");
        }

        if (expense.getCategory() == null ||
                expense.getCategory().trim().isEmpty()) {

            throw new IllegalArgumentException("Category is required");
        }

        if (expense.getDate() == null ||
                expense.getDate().trim().isEmpty()) {

            throw new IllegalArgumentException("Date is required");
        }
    }

    // Calculate dashboard summary for a specific user
    public Map<String, Double> getSummary(Long userId) {

        List<Expense> expenses =
                expenseRepository.findAllByUserId(userId);

        double totalIncome = 0;
        double totalExpense = 0;

        for (Expense expense : expenses) {

            if ("INCOME".equalsIgnoreCase(expense.getType())) {
                totalIncome += expense.getAmount();

            } else if ("EXPENSE".equalsIgnoreCase(expense.getType())) {
                totalExpense += expense.getAmount();
            }
        }

        double balance = totalIncome - totalExpense;

        Map<String, Double> summary = new HashMap<>();

        summary.put("totalIncome", totalIncome);
        summary.put("totalExpense", totalExpense);
        summary.put("balance", balance);

        return summary;
    }
}
