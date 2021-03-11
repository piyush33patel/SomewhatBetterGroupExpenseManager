package com.example.somewhatbettergroupexpensemanager;

public class Transaction {
    String description, date, id, amount, deleted;
    double totalRatio;
    Person persons[];
    public Transaction(){
        this.description = "description";
        this.date = "date";
        this.id = "-1";
        this.amount = "0";
        this.totalRatio = 0.0;
        this.deleted = "0";
        persons = new Person[5];
    }
}
