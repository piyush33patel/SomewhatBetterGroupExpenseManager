package com.example.somewhatbettergroupexpensemanager;

public class Transaction {
    String description, date;
    int id, amount;
    double totalRatio;
    public Transaction(String description, String date, int id, int amount, double totalRatio){
        this.description = description;
        this.date = date;
        this.id = id;
        this.amount = amount;
        this.totalRatio = totalRatio;
    }
}
