
package com.example.Springdemo.model;

public class Expense {

    private Long id;

    private Long userId;

    private String title;

    private double amount;

    private String type;

    private String category;

    private String date;

    private String description;

    public Expense() {

    }

    public Expense(Long id, String title, double amount,
                   String type, String category,
                   String date, String description) {

        this.id = id;
        this.title = title;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
