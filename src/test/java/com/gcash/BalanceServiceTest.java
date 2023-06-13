package com.gcash;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class BalanceServiceTest {

    AccountRepository repository;
    BalanceService balanceService;

    @BeforeEach
    void setup(){
        System.out.println("Setting up...");
        repository = new AccountRepository();
        balanceService = new BalanceService(repository);
    }
    @AfterEach
    void cleanup() {
        System.out.println("Cleaning up...");
        repository.deleteAllAccounts();
    }

    @BeforeAll
    static void globalSetup() {
        System.out.println("Global setup");
    }

    @AfterAll
    static void globalCleaning() {
        System.out.println("Global cleaning");
    }

    @Test
    void setBalanceTest() {
        Account account = new Account("1", "Orvyl", 100.0);
        account.setBalance(150.0);
        Assertions.assertEquals(150.0, account.balance());
    }

    @Test
    void getBalanceTest() throws AccountNotFoundException{
        String accountId = repository.createAccount("Orvyl", 89.9);
        Assertions.assertEquals(89.9, balanceService.getBalance(accountId));
    }

    @Test
    void getBalanceNonExistentAccountTest() {
        double initialBalance = 100.0;
        repository.createAccount("Orvyl", initialBalance);
        Assertions.assertThrows(AccountNotFoundException.class, () -> balanceService.getBalance("random id"));
    }

    @Test
    void creditTest() throws  AccountNotFoundException {
        String accountId = repository.createAccount("Orvyl", 50.0);
        balanceService.credit(accountId, 100.0);
        Double balance = balanceService.getBalance(accountId);
        Assertions.assertEquals(150.0, balance);
    }

    @Test
    void debitTest() throws InsufficientBalanceException, AccountNotFoundException {
        String accountId = repository.createAccount("Orvyl", 150.0);
        balanceService.debit(accountId, 100.0);
        Double balance = balanceService.getBalance(accountId);
        Assertions.assertEquals(50.0, balance);
    }

    @Test
    void transferToAndFromTest() throws InsufficientBalanceException, AccountNotFoundException {
        String senderAccountId = repository.createAccount("Orvyl", 150.0);
        String receiverAccountId = repository.createAccount("Siglo", 100.0);
        balanceService.transfer(senderAccountId, receiverAccountId, 50.0);
        Double senderBalance = balanceService.getBalance(senderAccountId);
        Double receiverBalance = balanceService.getBalance(receiverAccountId);
        Assertions.assertEquals(100.0, senderBalance);
        Assertions.assertEquals(150.0, receiverBalance);
    }


    @Test
    void debitAccountNotFoundTest()  {
        String accountId = repository.createAccount("Orvyl", 150.0);
        double deductedAmount = 1000.0;
        Assertions.assertThrows(AccountNotFoundException.class, () -> balanceService.debit("random id", deductedAmount));
    }
    @Test
    void insufficientBalanceTest()  {
        String accountId = repository.createAccount("Orvyl", 150.0);
        double debitedAmount = 1000;
        Assertions.assertThrows(InsufficientBalanceException.class, () -> balanceService.debit(accountId, debitedAmount));
    }

    @Test
    void creditAccountNotFoundTest()  {
        String accountId = repository.createAccount("Orvyl", 150.0);
        double addedAmount = 1000;
        Assertions.assertThrows(AccountNotFoundException.class, () -> balanceService.credit("random id", addedAmount));
    }

    @Test
    void insufficientTransferSenderTest()  {
        String receiverAccountId = repository.createAccount("Siglo", 100.0);
        String senderAccountId = repository.createAccount("Orvyl", 0.0);
        double amount = 50;
        Assertions.assertThrows(InsufficientBalanceException.class, () -> balanceService.transfer( senderAccountId, receiverAccountId, amount));
    }

    @Test
    void accountNotFoundTest()  {
        String receiverAccountId = repository.createAccount("Siglo", 100.0);
        String senderAccountId = repository.createAccount("Orvyl", 0.0);
        double amount = 50;
        Assertions.assertThrows(AccountNotFoundException.class, () -> balanceService.transfer( "siglo", receiverAccountId, amount));
    }

}
