package com.gcash.newfeatures;
import com.gcash.*;

public class BillsPayment {
    private BalanceService balanceService;
    private TransactionRepository transactionRepository;

    // added a constructor to take in existing BalanceService and TransactionHistory objects
    public BillsPayment(BalanceService balanceService, TransactionRepository transactionRepository) {
        this.balanceService = balanceService;
        this.transactionRepository = transactionRepository;
    }

    // a method to pay bills using an external account number
    public void payBill(String id, String billerName, Double amount) throws InsufficientBalanceException, AccountNotFoundException {
        // subtract the amount from the account
        this.balanceService.debit(id, amount);

        // add the transaction to the list of all transactions
        var billerTransaction = new Transaction(amount, Transaction.TransactionType.CREDIT);
        this.transactionRepository.addTransaction(billerName, billerTransaction);
    }
}

