package com.gcash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class AccountRepositoryTest {
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() throws NumberMustBeElevenDigit, NumberCannotBeEmpty, NumberAlreadyExists, NameCannotBeEmpty {
        accountRepository = new AccountRepository();
    }

    // TEST FOR USERS REGISTRATION
    @Test
    @DisplayName("Successful User Registration")
    void testUserRegistration() throws NumberMustBeElevenDigit, NumberCannotBeEmpty, NumberAlreadyExists, NameCannotBeEmpty {

        accountRepository.userRegistration("09175861666", "John", 200.0);

        Account account = accountRepository.getAccount("09175861666");
        Assertions.assertNotNull(account);
        Assertions.assertEquals("John", account.getName());
        Assertions.assertEquals(200.0, account.getBalance(), 0.0);
    }

    // TEST FOR REGISTERED ACCOUNT W/ EMPTY NAME
    @Test
    @DisplayName("Successful Testing Of Empty Name ")
    void testRegisterAccountWithEmptyName() {
        Assertions.assertThrows(NameCannotBeEmpty.class, () ->
                accountRepository.userRegistration("09175861666", "", 100.0));
    }

    // TEST FOR REGISTERED ACCOUNT W/ INVALID NUMBER
    @Test
    @DisplayName("Successful Testing Of Invalid Number ")
    void testRegisterAccountWithInvalidNumber() {
        Assertions.assertThrows(NumberMustBeElevenDigit.class, () ->
                accountRepository.userRegistration("1234567890", "John Doe", 100.0));
    }

    // TEST FOR REGISTERED ACCOUNT W/ EMPTY NUMBER
    @Test
    @DisplayName("Successful Testing Of Empty Number ")
    void testRegisterAccountWithEmptyNumber() {
        Assertions.assertThrows(NumberCannotBeEmpty.class, () ->
                accountRepository.userRegistration("", "John Doe", 100.0));
    }


    // TEST FOR REGISTERED ACCOUNT W/ EXISTING NUMBER
    @Test
    @DisplayName("Successful Testing Of ExistingNumber ")
    void testRegisterAccountWithExistingNumber() throws NumberMustBeElevenDigit, NumberCannotBeEmpty, NumberAlreadyExists, NameCannotBeEmpty {
        accountRepository.userRegistration("09175861666", "John Doe", 100.0);
        Assertions.assertThrows(NumberAlreadyExists.class, () ->
                accountRepository.userRegistration("09175861661", "John Doe", 100.0));
    }

    // TEST FOR GETTING THE ACCOUNT
    @Test
    @DisplayName("Successful Getting Users Account ")
    void testGetAccount() {
        Account account = accountRepository.getAccount("09175861661");
        Assertions.assertNotNull(account);
        Assertions.assertEquals("Bob", account.getName());
        Assertions.assertEquals(100.0, account.getBalance(), 0.0);

        account = accountRepository.getAccount("09175861667");
        Assertions.assertNull(account);
    }

    // TEST FOR UPDATING THE USER

    // TEST FOR DISPLAY ALL
    @Test
    @DisplayName("Displaying All Accounts")
    void testDisplayAll() throws NumberMustBeElevenDigit, NumberCannotBeEmpty, NumberAlreadyExists, NameCannotBeEmpty {
        // Invoke the displayAll method
        accountRepository.userRegistration("09175861666", "John Doe", 100.0);
        accountRepository.displayAll();
    }

}
