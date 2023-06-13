package com.gcash;

public class Account {
    //Instance Variable
    private String name;
    //    private String number;
    private Double balance;

    //Constructor
    public Account(String name, Double balance) {
        this.name = name;
//        this.number = number;
        this.balance = balance;
    }

    //Getter
    public String getName() {
        return name;
    }

//    public String getNumber() {
//        return number;
//    }

    public Double getBalance() {
        return balance;
    }

    //Setter
    public void setBalance(Double balance) {
        this.balance = balance;
    }
}