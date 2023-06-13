package com.gcash;

import java.util.Objects;

public class BalanceService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public BalanceService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Double getBalance(String id) throws AccountNotFoundException {
        Account foundAccount = accountRepository.getAccount(id);
        if (Objects.isNull(foundAccount)) {
            throw new AccountNotFoundException("Account " + id + " not found");
        }
        return foundAccount.getBalance();
    }

    public void debit(String id, Double amount) throws InsufficientBalanceException, AccountNotFoundException {
        Account foundAccount = accountRepository.getAccount(id);
        if (Objects.isNull(foundAccount)) {
            throw new AccountNotFoundException("Account " + id + " not found");
        }

        if (foundAccount.getBalance() >= amount) {
            foundAccount.setBalance(foundAccount.getBalance() - amount);
            var newDebitTransaction = new Transaction(amount, Transaction.TransactionType.DEBIT);
            transactionRepository.addTransaction(id, newDebitTransaction);
        } else {
            throw new InsufficientBalanceException("Insufficient balance!");
        }
    }

    public void credit(String id, Double amount) {
        Account foundAccount = accountRepository.getAccount(id);
        if (Objects.nonNull(foundAccount)) {
            foundAccount.setBalance(foundAccount.getBalance() + amount);
            var newCreditTransaction = new Transaction(amount, Transaction.TransactionType.CREDIT);
            transactionRepository.addTransaction(id, newCreditTransaction);
        }
    }

    public void transfer(String from, String to, Double amount) throws InsufficientBalanceException, AccountNotFoundException {
        debit(from, amount);
        credit(to, amount);
        var transferTransaction = new Transaction(amount, Transaction.TransactionType.TRANSFER);
        //kaso since transfer uses debit and credit, dodouble yung malalagay sa transaction
    }
}
