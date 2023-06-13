package com.gcash;

public class Account {
    //Instance Variable
    private String id;
    private String name;
    private String number;
    private Double balance;

    //Constructor
    public Account(String id, String name, String number, Double balance) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.balance = balance;
    }

    //Getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public Double getBalance() {
        return balance;
    }

    //Setter
    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
