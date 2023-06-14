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
    @DisplayName("Successful Testing Of Add Transaction ")
    void addTransaction() throws NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
//        Setup new accounts to be used for transaction
        accountRepository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        accountRepository.userRegistration("09617419365", "Orvyl2",0.0, "0000");
//        System.out.println(accountRepository.getAccount(id1));

//        Setup two new transactions
        var transaction1 = new Transaction(1.0, Transaction.TransactionType.DEBIT);
        var transaction2 = new Transaction(5.0, Transaction.TransactionType.CREDIT);
        transactionRepository.addTransaction("09617419366", transaction1);
        transactionRepository.addTransaction("09617419365", transaction2);

//        Call getAllTransactions and store the transactions in List testTransactions
        List<Transaction> testTransactions = transactionRepository.getAllTransactions();
//        System.out.println(testTransactions.get(0));
//        System.out.println(testTransactions.get(1));

//        Verify if the transactions stored in the List testTransactions matches the transaction in the variable
        Assertions.assertAll(
                () -> Assertions.assertEquals(transaction1, testTransactions.get(0)),
                () -> Assertions.assertEquals(transaction2, testTransactions.get(1))
        );
    }

    @Test
    @DisplayName("Successful Testing Of Get User Transactions ")
    void getUserTransactions() throws AccountNotFoundException, NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
//        Setup new accounts to be used for transaction
        accountRepository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        accountRepository.userRegistration("09617419365", "Orvyl2",0.0, "0000");

//        Setup three new transactions
        var transaction1 = new Transaction(1.0, Transaction.TransactionType.DEBIT);
        var transaction2 = new Transaction(5.0, Transaction.TransactionType.CREDIT);
        var transaction3 = new Transaction(1.0, Transaction.TransactionType.CREDIT);

//        Add transactions 1 and 3 to 09617419366, add transaction 2 to 09176419365
        transactionRepository.addTransaction("09617419366", transaction1);
        transactionRepository.addTransaction("09617419365", transaction2);
        transactionRepository.addTransaction("09617419366", transaction3);

//        Verify if the transactions added to the numbers matches the number of transactions stored in the user/number
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, transactionRepository.getUserTransactions("09617419366").size()),
                () -> Assertions.assertEquals(1, transactionRepository.getUserTransactions("09617419365").size())
        );
    }

    @Test
    @DisplayName("Successful Testing Of Get All Transactions ")
    void getAllTransactions() throws AccountNotFoundException, NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
//        Setup new accounts to be used for transactions
        accountRepository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        accountRepository.userRegistration("09617419365", "Orvyl2",0.0, "0000");

//        Setup three new transactions
        var transaction1 = new Transaction(1.0, Transaction.TransactionType.DEBIT);
        var transaction2 = new Transaction(5.0, Transaction.TransactionType.CREDIT);
        var transaction3 = new Transaction(1.0, Transaction.TransactionType.CREDIT);

//        Add transactions 1 and 3 to 09617419366, add transaction 2 to 09176419365
        transactionRepository.addTransaction("09617419366", transaction1);
        transactionRepository.addTransaction("09617419365", transaction2);
        transactionRepository.addTransaction("09617419366", transaction3);

//        System.out.println(transactionRepository.getUserTransactions(id1));
//        System.out.println(transactionRepository.getUserTransactions(id2));

//        Call getAllTransactions and store the transactions in List testTransactions
        List<Transaction> testTransactions = transactionRepository.getAllTransactions();

//        Verify if the size of List testTransactions matches the expected value (3)
        Assertions.assertEquals(3, testTransactions.size());
    }

    @Test
    @DisplayName("Successful Testing Of Get Transaction By Type ")
    void getTransactionsByType() throws AccountNotFoundException, NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
//        Setup new accounts to be used for transactions
        accountRepository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        accountRepository.userRegistration("09617419365", "Orvyl2",0.0, "0000");

//        Setup three new transactions (2 TRANSFER, 1 CREDIT)
        var transaction1 = new Transaction(1.0, Transaction.TransactionType.TRANSFER);
        var transaction2 = new Transaction(5.0, Transaction.TransactionType.TRANSFER);
        var transaction3 = new Transaction(1.0, Transaction.TransactionType.CREDIT);

//        Add transactions 1 and 3 to 09617419366, add transaction 2 to 09176419365
        transactionRepository.addTransaction("09617419366", transaction1);
        transactionRepository.addTransaction("09617419365", transaction2);
        transactionRepository.addTransaction("09617419366", transaction3);

//        Verify the number of transactions by type by checking if it matches the expected value of 2 TRANSFER, 1 CREDIT transactions
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, transactionRepository.getTransactionsByType(transactionRepository, Transaction.TransactionType.CREDIT).size()),
                () -> Assertions.assertEquals(2, transactionRepository.getTransactionsByType(transactionRepository, Transaction.TransactionType.TRANSFER).size())
        );
    }


    @Test
    @DisplayName("Unsuccessful Transaction Get If User Has No Transactions ")
    void noUserTransactionsToGet() throws AccountNotFoundException, NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException{
//        Setup new account to be used for transaction
        accountRepository.userRegistration("09617419366", "Orvyl",0.0, "0000");

//        Check if specific number has no transactions by calling the isEmpty method, and storing it in boolean checkContents
        boolean checkContents = transactionRepository.getUserTransactions("09617419366").isEmpty();

//        Verify that boolean checkContents is empty if true
        Assertions.assertTrue(checkContents);
    }

    @Test
    @DisplayName("Unsuccessful Transaction Get If No Transactions ")
    void noTransactionsAtAll() throws AccountNotFoundException, NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException{
//        Call getAllTransactions and store the transactions in List TestTransactions
        List<Transaction> testTransactions = transactionRepository.getAllTransactions();

//        Check if the list testTransactions is empty by calling the isEmpty method, and storing it in boolean checkContents
        boolean checkContents = testTransactions.isEmpty();

//        Verify that the boolean checkContents is empty if true
        Assertions.assertTrue(checkContents);
    }

}
