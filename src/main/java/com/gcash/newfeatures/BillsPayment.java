package com.gcash.newfeatures;
import com.gcash.*;

public class BillsPayment {
    private BalanceService balanceService;
    //private TransactionHistory transactionHistory;

    // added a constructor to take in existing BalanceService and TransactionHistory objects
    public BillsPayment(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    // a method to pay bills using an external account number
    public void payBill(String id, String billerName, Double amount) throws InsufficientBalanceException, AccountNotFoundException {
        // subtract the amount from the account
        this.balanceService.debit(id, amount);

        // add the transaction to the list of all transactions

        //(Date) datePosted = getDateToday(); pseudocode for a date getter
        // optional transactionId field that can be generated here or in the add() function itself instead
        //this.transactionHistory.add(id, billerName, amount, datePosted, [optional] transactionId);

    }
}
