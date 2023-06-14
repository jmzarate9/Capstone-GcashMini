package com.gcash.newfeatures;

import com.gcash.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class BillsPaymentTest {
    AccountRepository accountRepository;
    BalanceService balanceService;

    TransactionRepository transactionRepository;
    BillsPayment billsPayment;

    @BeforeEach
    void setup() {
        System.out.println("Setting up...");
        accountRepository = new AccountRepository();
        transactionRepository = new TransactionRepository();
        balanceService = new BalanceService(accountRepository, transactionRepository);
        billsPayment = new BillsPayment(balanceService, transactionRepository);
    }

    @Test
    void payBill() throws NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException, InsufficientBalanceException, AccountNotFoundException {
        accountRepository.userRegistration("09617419366", "Orvyl",100.0, "0000");

        // verify that the user transaction history is initially empty
        Assertions.assertTrue(transactionRepository.getUserTransactions("09617419366").isEmpty());

        // run the payBill method
        billsPayment.payBill("09617419366", "Meralco", 50.0);

        // verify that the user transaction has 1 entry
        Assertions.assertEquals(1, transactionRepository.getUserTransactions("09617419366").size());

        // verify that the biller transaction has 1 entry
        Assertions.assertEquals(1, transactionRepository.getUserTransactions("Meralco").size());

        for (Transaction transaction: transactionRepository.getAllTransactions()) {
            System.out.println(transaction);
        }

        // verify that the user's new balance is decreased to 50.0
        Assertions.assertEquals(50.0, balanceService.getBalance("09617419366"));

    }
}

