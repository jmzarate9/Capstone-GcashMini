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
    void setBalanceTest() {
        Account account = new Account("Orvyl", 100.0, "0000");
        account.setBalance(150.0);
        Assertions.assertEquals(150.0, account.getBalance());
    }

    @Test
    void getBalanceTest() throws AccountNotFoundException,  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",100.0, "0000");
        Assertions.assertEquals(100.0, balanceService.getBalance("09617419366"));
    }

    @Test
    void getBalanceNonExistentAccountTest() throws  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        Assertions.assertThrows(AccountNotFoundException.class, () -> balanceService.getBalance("random id"));
    }

    @Test
    void creditTest() throws AccountNotFoundException,  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",50.0, "0000");
        balanceService.credit("09617419366", 100.0);
        Double balance = balanceService.getBalance("09617419366");
        Assertions.assertEquals(150.0, balance);
    }

    @Test
    void debitTest() throws InsufficientBalanceException, AccountNotFoundException,  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",150.0, "0000");
        balanceService.debit("09617419366", 100.0);
        Double balance = balanceService.getBalance("09617419366");
        Assertions.assertEquals(50.0, balance);
    }

    @Test
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
    void debitAccountNotFoundTest() throws  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        double deductedAmount = 1000.0;
        Assertions.assertThrows(AccountNotFoundException.class, () -> balanceService.debit("random id", deductedAmount));
    }
    @Test
    void insufficientBalanceTest() throws  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        double debitedAmount = 1000.0;
        Assertions.assertThrows(InsufficientBalanceException.class, () -> balanceService.debit("09617419366", debitedAmount));
    }

    @Test
    void creditAccountNotFoundTest() throws  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        double addedAmount = 1000;
        Assertions.assertThrows(AccountNotFoundException.class, () -> balanceService.credit("random id", addedAmount));
    }

    @Test
    void insufficientTransferSenderTest() throws  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",49.0, "0000");
        repository.userRegistration("09617419361", "Orvyl2",150.0, "0000");
        Assertions.assertThrows(InsufficientBalanceException.class, () -> balanceService.transfer( "09617419366", "09617419361", 50.0));
    }

    @Test
    void accountTransferNotFoundTest() throws  NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException, NameCannotBeEmptyException {
        repository.userRegistration("09617419366", "Orvyl",0.0, "0000");
        repository.userRegistration("09617419361", "Orvyl2",0.0, "0000");
        double amount = 50;
        Assertions.assertThrows(AccountNotFoundException.class, () -> balanceService.transfer( "siglo", "Corvyl", amount));
    }
}


