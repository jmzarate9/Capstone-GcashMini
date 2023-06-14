package com.gcash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class AccountRepositoryTest {
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() throws NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, NameCannotBeEmptyException {
        System.out.println("Setting up...");
        accountRepository = new AccountRepository();
    }

    // TEST FOR USERS REGISTRATION
    @Test
    @DisplayName("Successful User Registration")
    void testUserRegistration() throws NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, NameCannotBeEmptyException {

        // Register a new user with the given details
        accountRepository.userRegistration("09175861666", "John", 200.0);

        // Retrieve the registered account using the phone number
        Account account = accountRepository.getAccount("09175861666");
        // Verify if the account is not null
        Assertions.assertNotNull(account);
        // Verify if the account's name matches the expected value
        Assertions.assertEquals("John", account.getName());
        // Verify if the account's balance matches the expected value
        Assertions.assertEquals(200.0, account.getBalance(), 0.0);
    }

    // TEST FOR REGISTERED ACCOUNT W/ EMPTY NAME
    @Test
    @DisplayName("Successful Testing Of Empty Name ")
    void testRegisterAccountWithEmptyName() {

        //Ensure that an exception of type NameCannotBeEmptyException is raised when the name is empty.
        Assertions.assertThrows(NameCannotBeEmptyException.class, () ->
                accountRepository.userRegistration("09175861666", "", 100.0));
    }

    // TEST FOR REGISTERED ACCOUNT W/ INVALID NUMBER
    @Test
    @DisplayName("Successful Testing Of Invalid Number ")
    void testRegisterAccountWithInvalidNumber() {

        //Ensure that an exception of type NumberMustBeElevenDigitsException is raised when the phoneNumber is greater than 11.
        Assertions.assertThrows(NumberMustBeElevenDigitsException.class, () ->
                accountRepository.userRegistration("1234567890", "John Doe", 100.0));
    }

    // TEST FOR REGISTERED ACCOUNT W/ EMPTY NUMBER
    @Test
    @DisplayName("Successful Testing Of Empty Number ")
    void testRegisterAccountWithEmptyNumber() {

        // Ensure that an exception of type NumberCannotBeEmptyException is raised when the phoneNumber is empty.
        Assertions.assertThrows(NumberCannotBeEmptyException.class, () ->
                accountRepository.userRegistration("", "John Doe", 100.0));
    }


    // TEST FOR REGISTERED ACCOUNT W/ EXISTING NUMBER
    @Test
    @DisplayName("Successful Testing Of ExistingNumber ")
    void testRegisterAccountWithExistingNumber() throws NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, NameCannotBeEmptyException {

        // Register a new account with a specific phone number
        accountRepository.userRegistration("09175861666", "John Doe", 100.0);

        // Verify that trying to register an account with an existing phone number throws the expected exception
        Assertions.assertThrows(NumberAlreadyExistsException.class, () ->
                accountRepository.userRegistration("09175861661", "John Doe", 100.0));
    }

    // TEST FOR GETTING THE ACCOUNT
    @Test
    @DisplayName("Successful Getting Users Account ")
    void testGetAccount() {

        // Get the account with the specified phone number
        Account account = accountRepository.getAccount("09175861661");

        // Verify if the account with a non-existent phone number is null
        Assertions.assertNull(accountRepository.getAccount("09175861667"), "The account should be null");

        //Verify if the account with the specified phone number is not null
        Assertions.assertNotNull(account);
        // Verify if the account's name matches the expected value
        Assertions.assertEquals("Bob", account.getName());
        // Verify if the account's balance matches the expected value
        Assertions.assertEquals(100.0, account.getBalance());

    }

    // TEST FOR SEARCHING AN ACCOUNT
    @Test
    @DisplayName("Successful Account Search")
    void testSearchAccount(){

        // Search for an existing account
        Account account = accountRepository.searchAccount("09175861662");

        // Verify if the account is not null
        Assertions.assertNotNull(account);
        Assertions.assertEquals("Marley", account.getName());
        Assertions.assertEquals(100.0, account.getBalance());

        // Search for a non-existent account
        Account nonExistentAccount = accountRepository.searchAccount("09175861669");

        // Verify if the non-existent account is null
        Assertions.assertNull(nonExistentAccount);
    }


    // TEST FOR DISPLAY ALL
    @Test
    @DisplayName("Successful Displaying All Accounts")
    void testDisplayAll() throws NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, NameCannotBeEmptyException {

        // Register a new account
        accountRepository.userRegistration("09175861666", "John Doe", 100.0);

        // Invoke the displayAll method
        accountRepository.displayAll();
    }

    // TEST FOR GETTING THE NUMBER OF ACCOUNTS
    @Test
    @DisplayName("Successful Getting Number of Registered Users")
    void testGetNumberOfRegisteredUsers() throws NumberMustBeElevenDigitsException, NumberCannotBeEmptyException,
            NumberAlreadyExistsException, NameCannotBeEmptyException {

        // Get the initial number of registered users
        int initialNumberOfUsers = accountRepository.getNumberOfRegisteredUsers();

        // Register a new account
        accountRepository.userRegistration("09175861666", "John Doe", 100.0);

        // Verify the expected value
        Assertions.assertEquals(initialNumberOfUsers + 1, accountRepository.getNumberOfRegisteredUsers());
    }

    // TEST FOR ACCOUNT DELETION
    @Test
    @DisplayName("Successful Account Deletion")
    void testDeleteAccount() {

        //Delete the user account
        accountRepository.deleteAccount("09175861662");

        //Check whether the deleted user account is no longer present in the database
        Account account = accountRepository.searchAccount("09175861662");
        Assertions.assertNull(account);

        accountRepository.displayAll();
    }

    // TEST FOR ALL ACCOUNT DELETION
    @Test
    @DisplayName("Successful Deleting All Accounts")
    void testDeleteAllAccounts() {

        //Delete all account and create a variable named "count" to obtain the total number of registered accounts.
        accountRepository.deleteAll();
        int count = accountRepository.getNumberOfRegisteredUsers();

        //Verify the expected value
        Assertions.assertEquals(0, count);

        //Print and display
        System.out.println("All accounts deleted successfully.");
        accountRepository.displayAll();
    }

}
