package com.example.somewhatbettergroupexpensemanager;

public class Transaction {
    String description, date;
    int id, amount;
    double totalRatio;
    boolean deleted;
    Person persons[];
    public Transaction(){
        this.description = "description";
        this.date = "date";
        this.id = -1;
        this.amount = 0;
        this.totalRatio = 0.0;
        this.deleted = false;
        persons = new Person[5];
    }
}
