package com.gcash;

public class Account {
    //Instance Variable
    private String name;
    private Double balance;
    private String passcode;

    //Constructor
    public Account(String name, Double balance,  String passcode) {
        this.name = name;
        this.balance = balance;
        this.passcode = passcode;
    }

    //Getter
    public String getName() {
        return name;
    }

    public Double getBalance() {
        return balance;
    }

    public String getPasscode() {
        return passcode;
    }

    //Setter
    public void setBalance(Double balance) {
        this.balance = balance;
    }
}