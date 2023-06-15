package com.gcash.newfeatures;

import com.gcash.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GForestTest {
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
    void plantATree() throws NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException, InsufficientBalanceException, AccountNotFoundException {
        accountRepository.userRegistration("09617419366", "Orvyl",100.0, "0000");
        GForest gForest = new GForest("09617419366", transactionRepository);
        // verify that the user transaction history is initially empty
        Assertions.assertTrue(transactionRepository.getUserTransactions("09617419366").isEmpty());

        // run the payBill method
        billsPayment.payBill("09617419366", "Meralco", 50.0);
        gForest.updateEnergy(transactionRepository);

        // verify that running the plantATree method returns false
        Assertions.assertFalse(gForest.plantATree(transactionRepository));

        // run the payBill method 15 times
        for (int i = 0; i < 15; i++) {
            billsPayment.payBill("09617419366", "Meralco", 1.0);
        }

        // verify that the account earned 2250 points
        gForest.updateEnergy(transactionRepository);
        Assertions.assertEquals(2400, gForest.getEnergy());

        // verify that running the plantATree method returns true
        Assertions.assertTrue(gForest.plantATree(transactionRepository));

        // verify that the remaining balance is 900
        Assertions.assertEquals(900, gForest.getEnergy());

    }

    @Test
    public void updateEnergy() throws NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException, InsufficientBalanceException, AccountNotFoundException {
        accountRepository.userRegistration("09617419366", "Orvyl",100.0, "0000");
        GForest gForest = new GForest("09617419366", transactionRepository);

        // verify that the user transaction history is initially empty
        Assertions.assertTrue(transactionRepository.getUserTransactions("09617419366").isEmpty());

        // verify that there is no initial energy for a user with no transactions
        Assertions.assertEquals(0, gForest.getEnergy());

        // verify that updating the transactions will reflect after running updateEnergy()
        billsPayment.payBill("09617419366", "Meralco", 50.0);
        gForest.updateEnergy(transactionRepository);
        Assertions.assertEquals(150, gForest.getEnergy());

    }

    @Test
    public void getEnergy() throws NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        accountRepository.userRegistration("09617419366", "Orvyl",100.0, "0000");
        GForest gForest = new GForest("09617419366", transactionRepository);
        Assertions.assertEquals(0, gForest.getEnergy());
    }

}
