package com.gcash;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class TransactionRepositoryTest {

    AccountRepository accountRepository;
    BalanceService balanceService;

    TransactionRepository transactionRepository;

    @BeforeEach
    void setup() {
        System.out.println("Setting up...");
        accountRepository = new AccountRepository();
        transactionRepository = new TransactionRepository();
        balanceService = new BalanceService(accountRepository, transactionRepository);
    }
    /*
        @AfterEach
        void cleanup() {
            System.out.println("Cleaning up...");
            accountRepository.deleteAllAccounts();
        }
    */
    @BeforeAll
    static void globalSetup() {
        System.out.println("Global setup");
    }

    @AfterAll
    static void globalCleaning() {
        System.out.println("Global cleaning");
    }

    @Test
    void addTransaction() throws NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        accountRepository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        accountRepository.userRegistration("09617419365", "Orvyl2",0.0, "0000");
        //System.out.println(accountRepository.getAccount(id1));

        var transaction1 = new Transaction(1.0, Transaction.TransactionType.DEBIT);
        var transaction2 = new Transaction(5.0, Transaction.TransactionType.CREDIT);
        transactionRepository.addTransaction("09617419366", transaction1);
        transactionRepository.addTransaction("09617419365", transaction2);

        List<Transaction> testTransactions = transactionRepository.getAllTransactions();
//        System.out.println(testTransactions.get(0));
//        System.out.println(testTransactions.get(1));
        Assertions.assertAll(
                () -> Assertions.assertEquals(transaction1, testTransactions.get(0)),
                () -> Assertions.assertEquals(transaction2, testTransactions.get(1))
        );
    }

    @Test
    void getUserTransactions() throws AccountNotFoundException, NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        accountRepository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        accountRepository.userRegistration("09617419365", "Orvyl2",0.0, "0000");

        var transaction1 = new Transaction(1.0, Transaction.TransactionType.DEBIT);
        var transaction2 = new Transaction(5.0, Transaction.TransactionType.CREDIT);
        var transaction3 = new Transaction(1.0, Transaction.TransactionType.CREDIT);

        transactionRepository.addTransaction("09617419366", transaction1);
        transactionRepository.addTransaction("09617419365", transaction2);
        transactionRepository.addTransaction("09617419366", transaction3);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, transactionRepository.getUserTransactions("09617419366").size()),
                () -> Assertions.assertEquals(1, transactionRepository.getUserTransactions("09617419365").size())
        );
    }

    @Test
    void getAllTransactions() throws AccountNotFoundException, NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        accountRepository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        accountRepository.userRegistration("09617419365", "Orvyl2",0.0, "0000");

        var transaction1 = new Transaction(1.0, Transaction.TransactionType.DEBIT);
        var transaction2 = new Transaction(5.0, Transaction.TransactionType.CREDIT);
        var transaction3 = new Transaction(1.0, Transaction.TransactionType.CREDIT);

        transactionRepository.addTransaction("09617419366", transaction1);
        transactionRepository.addTransaction("09617419365", transaction2);
        transactionRepository.addTransaction("09617419366", transaction3);

//        System.out.println(transactionRepository.getUserTransactions(id1));
//        System.out.println(transactionRepository.getUserTransactions(id2));

        List<Transaction> testTransactions = transactionRepository.getAllTransactions();
        Assertions.assertEquals(3, testTransactions.size());
    }

    @Test
    void getTransactionsByType() throws AccountNotFoundException, NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        accountRepository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        accountRepository.userRegistration("09617419365", "Orvyl2",0.0, "0000");

        var transaction1 = new Transaction(1.0, Transaction.TransactionType.TRANSFER);
        var transaction2 = new Transaction(5.0, Transaction.TransactionType.TRANSFER);
        var transaction3 = new Transaction(1.0, Transaction.TransactionType.CREDIT);

        transactionRepository.addTransaction("09617419366", transaction1);
        transactionRepository.addTransaction("09617419365", transaction2);
        transactionRepository.addTransaction("09617419366", transaction3);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, transactionRepository.getTransactionsByType(transactionRepository, Transaction.TransactionType.CREDIT).size()),
                () -> Assertions.assertEquals(2, transactionRepository.getTransactionsByType(transactionRepository, Transaction.TransactionType.TRANSFER).size())
        );
    }


    @Test
    void noUserTransactionsToGet() throws AccountNotFoundException, NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException{
        accountRepository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        boolean checkContents = transactionRepository.getUserTransactions("09617419366").isEmpty();
        Assertions.assertTrue(checkContents);
    }

    @Test
    void noTransactionsAtAll() throws AccountNotFoundException, NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException{
        List<Transaction> testTransactions = transactionRepository.getAllTransactions();
        boolean checkContents = testTransactions.isEmpty();
        Assertions.assertTrue(checkContents);
    }

}
