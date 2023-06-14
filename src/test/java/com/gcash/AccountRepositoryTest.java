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
    void testUserRegistration() throws NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, NameCannotBeEmptyException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException {

        // Register a new user with the given details
        accountRepository.userRegistration("09175861666", "John", 200.0, "0000");

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
                accountRepository.userRegistration("09175861666", "", 100.0, "0000"), "Name should be empty");
    }

    // TEST FOR REGISTERED ACCOUNT W/ INVALID NUMBER
    @Test
    @DisplayName("Successful Testing Of Invalid Number ")
    void testRegisterAccountWithInvalidNumber() {

        //Ensure that an exception of type NumberMustBeElevenDigitsException is raised when the phoneNumber is greater than 11.
        Assertions.assertThrows(NumberMustBeElevenDigitsException.class, () ->
                accountRepository.userRegistration("09175861666000", "John Doe", 100.0, "0000"), "The number should be Invalid or less than 11 digit");
    }

    // TEST FOR REGISTERED ACCOUNT W/ EMPTY NUMBER
    @Test
    @DisplayName("Successful Testing Of Empty Number ")
    void testRegisterAccountWithEmptyNumber() {

        // Ensure that an exception of type NumberCannotBeEmptyException is raised when the phoneNumber is empty.
        Assertions.assertThrows(NumberCannotBeEmptyException.class, () ->
                accountRepository.userRegistration("", "John Doe", 100.0, "0000"), "The number should be Empty.");
    }


    // TEST FOR REGISTERED ACCOUNT W/ EXISTING NUMBER
    @Test
    @DisplayName("Successful Testing Of ExistingNumber ")
    void testRegisterAccountWithExistingNumber() {

        // Verify that trying to register an account with an existing phone number throws the expected exception
        Assertions.assertThrows(NumberAlreadyExistsException.class, () ->
                accountRepository.userRegistration("09175861661", "John Doe", 100.0, "0000"), "The number should be existing.");
    }

    // TEST FOR USER REGISTRATION W/ EMPTY PASSCODE
    @Test
    @DisplayName("Successful Testing of Empty MPIN")
    void testEmptyPasscode() {

        // Verify the exception
        Assertions.assertThrows(PasscodeCannotBeEmptyException.class, () -> accountRepository.userRegistration("09175861669", "John Doe", 100.0, ""), "The MPIN / Passcode should be Empty");
    }

    // TEST FOR USER REGISTRATION W/ LESS THAN 4 DIGIT PASSCODE
    @Test
    @DisplayName("Successful Testing of Less than 4 digit MPIN")
    void testLessThanFourDigitPasscode() {

        // Verify the exception
        Assertions.assertThrows(PasscodeShouldFourDigitsException.class, () -> accountRepository.userRegistration("09175861669", "John Doe", 100.0, "12"), "The MPIN / Passcode should be less than 4 digit");
    }

    // TEST FOR USER REGISTRATION W/ INVALID PASSCODE
    @Test
    @DisplayName("Successful Testing of Invalid MPIN")
    void testInvalidPasscode() {

        // Ensure that an exception of type PasscodeShouldFourDigitsException is raised when the passcode is not four digits.
        Assertions.assertThrows(PasscodeShouldFourDigitsException.class, () ->
                accountRepository.userRegistration("09175861666", "John Doe", 100.0, "12345"), "The passcode should be invalid.");
    }

    //TEST FOR LOGIN ACCOUNT
    @Test
    @DisplayName("Successful Testing of Login Feature")
    void testLoginDifferentNumberOrPasscode() {
        Assertions.assertThrows(LoginException.class, () -> accountRepository.logIn("09175861661", "0000"), "The number or MPIN should be different from the initial value.");
    }

    // TEST FOR RETRIEVING ACCOUNT PASSCODE
    @Test
    @DisplayName("Successful Retrieval of Passcode")
    void testGetPasscode() {
        // Register a new account with a passcode
        try {
            accountRepository.userRegistration("09175861666", "John Doe", 100.0, "1234");
        } catch (Exception e) {
            // Handle exceptions if necessary
        }

        // Retrieve the account from the repository
        Account account = accountRepository.getAccount("09175861666");

        // Retrieve the passcode using the getPasscode() method
        String passcode = account.getPasscode();

        // Verify if the retrieved passcode matches the expected value
        Assertions.assertEquals("1234", passcode);
    }

    // TEST FOR LOGIN ACCOUNT
    @Test
    @DisplayName("Login")
    void testLogIn() throws NumberMustBeElevenDigitsException, NumberCannotBeEmptyException,
            NumberAlreadyExistsException, NameCannotBeEmptyException, PasscodeCannotBeEmptyException,
            PasscodeShouldFourDigitsException, LoginException {
        // Register an account
        accountRepository.userRegistration("09175861666", "John", 100.0, "0000");

        // Log in with correct phone number and passcode
        boolean loggedIn = accountRepository.logIn("09175861666", "0000");

        // Verify if the login was successful
        Assertions.assertTrue(loggedIn);

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
    void testDisplayAll() throws NumberAlreadyExistsException, NumberCannotBeEmptyException, NumberMustBeElevenDigitsException, NameCannotBeEmptyException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException {

        // Register a new account
        accountRepository.userRegistration("09175861666", "John Doe", 100.0, "0000");

        // Invoke the displayAll method
        accountRepository.displayAll();
    }

    // TEST FOR GETTING THE NUMBER OF ACCOUNTS
    @Test
    @DisplayName("Successful Getting Number of Registered Users")
    void testGetNumberOfRegisteredUsers() throws NumberMustBeElevenDigitsException, NumberCannotBeEmptyException,
            NumberAlreadyExistsException, NameCannotBeEmptyException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException {

        // Get the initial number of registered users
        int initialNumberOfUsers = accountRepository.getNumberOfRegisteredUsers();

        // Register a new account
        accountRepository.userRegistration("09175861666", "John Doe", 100.0, "0000");

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
