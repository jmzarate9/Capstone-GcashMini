package com.gcash;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;


public class BalanceServiceTest {

    AccountRepository repository;
    BalanceService balanceService;
    TransactionRepository transactionRepository;

    @BeforeEach
    void setup(){
        System.out.println("Setting up...");
        repository = new AccountRepository();
        transactionRepository = new TransactionRepository();
        balanceService = new BalanceService(repository, transactionRepository);

    }
    /*
    @AfterEach
    void cleanup() {
        System.out.println("Cleaning up...");
        repository.deleteAllAccounts();
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
    @DisplayName("Balance Set ")
    void setBalanceTest() {
        Account account = new Account("Orvyl", 100.0, "0000");
        account.setBalance(150.0);
        Assertions.assertEquals(150.0, account.getBalance());
    }

    @Test
    @DisplayName("Balance Get ")
    void getBalanceTest() throws AccountNotFoundException,  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",100.0, "0000");
        Assertions.assertEquals(100.0, balanceService.getBalance("09617419366"));
    }

    @Test
    @DisplayName("Account Non Existent ")
    void getBalanceNonExistentAccountTest() throws  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        Assertions.assertThrows(AccountNotFoundException.class, () -> balanceService.getBalance("random id"));
    }

    @Test
    @DisplayName("Amount Credited ")
    void creditTest() throws AccountNotFoundException,  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",50.0, "0000");
        balanceService.credit("09617419366", 100.0);
        Double balance = balanceService.getBalance("09617419366");
        Assertions.assertEquals(150.0, balance);
    }

    @Test
    @DisplayName("Amount Debited ")
    void debitTest() throws InsufficientBalanceException, AccountNotFoundException,  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",150.0, "0000");
        balanceService.debit("09617419366", 100.0);
        Double balance = balanceService.getBalance("09617419366");
        Assertions.assertEquals(50.0, balance);
    }

    @Test
    @DisplayName("Transfer Success")
    void transferToAndFromTest() throws InsufficientBalanceException, AccountNotFoundException,  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",150.0, "0000");
        repository.userRegistration("09617419361", "Orvyl2",100.0, "0000");
        balanceService.transfer("09617419366", "09617419361", 50.0);
        Double senderBalance = balanceService.getBalance("09617419366");
        Double receiverBalance = balanceService.getBalance("09617419361");
        Assertions.assertEquals(100.0, senderBalance);
        Assertions.assertEquals(150.0, receiverBalance);
    }


    @Test
    @DisplayName("Account Non Existent ")
    void debitAccountNotFoundTest() throws  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        double deductedAmount = 1000.0;
        Assertions.assertThrows(AccountNotFoundException.class, () -> balanceService.debit("random id", deductedAmount));
    }
    @Test
    @DisplayName("Insufficient Balance ")
    void insufficientBalanceTest() throws  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        double debitedAmount = 1000.0;
        Assertions.assertThrows(InsufficientBalanceException.class, () -> balanceService.debit("09617419366", debitedAmount));
    }

    @Test
    @DisplayName("Account Non Existent ")
    void creditAccountNotFoundTest() throws  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        double addedAmount = 1000;
        Assertions.assertThrows(AccountNotFoundException.class, () -> balanceService.credit("random id", addedAmount));
    }

    @Test
    @DisplayName("Insufficient Balance ")
    void insufficientTransferSenderTest() throws  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",49.0, "0000");
        repository.userRegistration("09617419361", "Orvyl2",150.0, "0000");
        Assertions.assertThrows(InsufficientBalanceException.class, () -> balanceService.transfer( "09617419366", "09617419361", 50.0));
    }

    @Test
    @DisplayName("Account Non Existent ")
    void accountTransferNotFoundTest() throws  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        repository.userRegistration("09617419361", "Orvyl2",0.0, "0000");
        double amount = 50;
        Assertions.assertThrows(AccountNotFoundException.class, () -> balanceService.transfer( "siglo", "Corvyl", amount));
    }
}


