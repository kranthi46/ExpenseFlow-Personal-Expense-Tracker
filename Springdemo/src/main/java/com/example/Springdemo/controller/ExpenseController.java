package com.example.Springdemo.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Springdemo.model.Expense;
import com.example.Springdemo.service.ExpenseService;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // Check whether the user is logged in
    private Long getLoggedInUserId(HttpSession session) {

        Object userId = session.getAttribute("userId");

        if (userId instanceof Number) {
            return ((Number) userId).longValue();
        }

        return null;
    }

    // 1. Get all transactions
    @GetMapping
    public ResponseEntity<?> getAllExpenses(HttpSession session) {

        Long userId = getLoggedInUserId(session);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Please login first");
        }

        List<Expense> expenses =
                expenseService.getAllExpenses(userId);

        return ResponseEntity.ok(expenses);
    }

    // 2. Get transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(
            @PathVariable Long id,
            HttpSession session) {

        Long userId = getLoggedInUserId(session);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Please login first");
        }

        try {

            Expense expense =
                    expenseService.getExpenseById(id, userId);

            return ResponseEntity.ok(expense);

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // 3. Add new transaction
    @PostMapping
    public ResponseEntity<?> addExpense(
            @RequestBody Expense expense,
            HttpSession session) {

        Long userId = getLoggedInUserId(session);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Please login first");
        }

        try {

            Expense savedExpense =
                    expenseService.addExpense(expense, userId);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedExpense);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 4. Update transaction
    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(
            @PathVariable Long id,
            @RequestBody Expense expense,
            HttpSession session) {

        Long userId = getLoggedInUserId(session);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Please login first");
        }

        try {

            Expense updatedExpense =
                    expenseService.updateExpense(id, expense, userId);

            return ResponseEntity.ok(updatedExpense);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // 5. Delete transaction
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(
            @PathVariable Long id,
            HttpSession session) {

        Long userId = getLoggedInUserId(session);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Please login first");
        }

        try {

            expenseService.deleteExpense(id, userId);

            return ResponseEntity.ok(
                    "Transaction deleted successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // 6. Get dashboard summary
    @GetMapping("/summary")
    public ResponseEntity<?> getSummary(HttpSession session) {

        Long userId = getLoggedInUserId(session);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Please login first");
        }

        Map<String, Double> summary =
                expenseService.getSummary(userId);

        return ResponseEntity.ok(summary);
    }
}
