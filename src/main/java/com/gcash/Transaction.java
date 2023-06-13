package com.gcash;

import java.util.HashMap;
import java.util.List;

public class Transaction {
    private Double amount;
    private TransactionType type;
    //transactions have two variables: amount transacted, and type of transaction

    public Transaction(Double amount, TransactionType type) {
        this.amount = amount;
        this.type = type;
    } //encapsulation


    public enum TransactionType { //since fixed naman yung transactions ni GCash mini
        DEBIT,
        CREDIT,
        TRANSFER
    }

    @Override //so the output is readable
    public String toString() {
        return type + " transaction of " + amount + " pesos";
    }

}
