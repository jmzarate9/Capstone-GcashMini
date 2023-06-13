package com.gcash;

import java.util.*;

public class TransactionRepository {

    private final Map<String, List<Transaction>> transactionHistory;
    //parang yung final project where the key is the id and the value is a list of transactions
    public TransactionRepository(){
        this.transactionHistory = new LinkedHashMap<>(); //LinkedHashMap to preserve order
    }

    public void addTransaction(String id, Transaction transaction) {
        transactionHistory.putIfAbsent(id, new ArrayList<>());
        //putIfAbsent and put are different
        transactionHistory.get(id).add(transaction);
    }

    public List<Transaction> getUserTransactions(String id) {
        return transactionHistory.getOrDefault(id, new ArrayList<>());
        //so if it checks the id and wala siya sa transactionHistory, you return a blank list
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> allTransactions = new ArrayList<>();
        for (List<Transaction> transactions : transactionHistory.values()) {
            allTransactions.addAll(transactions);
        }
        return allTransactions;
        //rather than directly return transactionHistory, for encapsulation, just iterate through all entries and add to a separate list
    }
}
