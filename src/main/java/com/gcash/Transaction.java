package com.gcash;

import java.time.LocalDateTime;

public class Transaction {
    private Double amount;
    private TransactionType type;
    //transactions have three variables: amount transacted, type of transaction, and the recipient (can be another account or a biller)
    private LocalDateTime timestamp;

    public Transaction(Double amount, TransactionType type) {
        this.amount = amount;
        this.type = type;
    } //encapsulation

    public TransactionType getType() {
        return type;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public enum TransactionType { //since fixed naman yung transactions ni GCash mini
        DEBIT,
        CREDIT,
        TRANSFER
    }

//    @Override //so the output is readable
//    public String toString() {
//        return type + " transaction of " + amount + " pesos at " + timestamp;
//    }

}
