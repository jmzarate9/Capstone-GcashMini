package com.gcash;

import java.util.HashMap;

public class AccountRepository {

    // Declares a private variable accounts of type HashMap that will store the account objects.
    private HashMap<String, Account> accounts;

    // Constructor for the AccountRepository class.
    public AccountRepository() {
        // Initializes the accounts variable as a new empty HashMap.
        accounts = new HashMap<>();
        // Calls the initializeAccounts() method to populate the accounts map with some predefined account data.
        initializeAccounts();
    }

    // initializeAccounts() - initializes the accounts with some predefined values or initial Users
    private void initializeAccounts() {

        // Adds a new account object with the phone number "09175861661", name "Bob", and initial balance 100.0 to the accounts map.
        accounts.put("09175861661", new Account("Bob", 100.0, "1234"));
        accounts.put("09175861662", new Account("Marley", 100.0, "2345"));
        accounts.put("09175861663", new Account("Seth", 100.0, "3456"));
        accounts.put("09175861664", new Account("Ryan", 100.0, "4567"));
        accounts.put("09175861665", new Account("Fritz", 100.0, "5678"));
    }

    // UsersRegistration
    public void userRegistration(String phoneNumber, String name, double initialBalance, String passcode) throws NumberMustBeElevenDigitsException, NumberCannotBeEmptyException, NumberAlreadyExistsException, NameCannotBeEmptyException, PasscodeCannotBeEmptyException, PasscodeShouldFourDigitsException {

        // Create conditional statements for validations
        if (phoneNumber.isEmpty()) {
            // Throws a NumberCannotBeEmptyException with an appropriate error message.
            throw new NumberCannotBeEmptyException("Phone number cannot be empty.");
        } else if (phoneNumber.length() != 11) {
            throw new NumberMustBeElevenDigitsException("Phone number must be eleven digits.");
        }else if (accounts.containsKey(phoneNumber)) {
            throw new NumberAlreadyExistsException("Phone number already exists.");
        }
        if (name.isEmpty()) {
            throw new NameCannotBeEmptyException("Name cannot be empty.");
        }
        if (passcode.isEmpty()) {
            throw new PasscodeCannotBeEmptyException("MPIN cannot be empty.");
        } else if (passcode.length() != 4) {
            throw new PasscodeShouldFourDigitsException("MPIN must be four digits.");
        }


        // Creates a new Account object with the provided name and initial balance.
        Account account = new Account(name, initialBalance, passcode);
        // Adds the newly created account to the accounts map with phoneNumber as the key.
        accounts.put(phoneNumber, account);
    }

    // Login
    public boolean logIn(String phoneNumber, String passcode) throws LoginException {
        Account account = accounts.get(phoneNumber);
        if (account == null || !account.getPasscode().equals(passcode)) {
            throw new LoginException("Incorrect number or MPIN");
        }
        account.setLoggedIn(true);
        return true;
    }

    // Sign Out
    public void signOut(String phoneNumber) {
        Account account = accounts.get(phoneNumber);
        if (account != null) {
            account.setLoggedIn(false);
        }
    }

    // Get Account
    // This method retrieves the account associated with the specified phone number.
    public Account getAccount(String phoneNumber) {
        return accounts.get(phoneNumber);
    }

    // Search Account
    // This method search for the user account using phoneNumber
    public Account searchAccount(String phoneNumber) {
        return accounts.get(phoneNumber);
    }

    // Display Account
    // This method displays all the registered accounts by iterating over the account HashMap and printing the phone number, name, and balance of each account.
    public void displayAll() {
        for (String phoneNumber : accounts.keySet()) {
            Account account = accounts.get(phoneNumber);
            System.out.println("Phone number: " + phoneNumber + ", Name: " + account.getName() + ", Balance: " + account.getBalance());
        }
    }

    // Get NumberOfRegisteredUsers
    //  This method returns the total number of registered users by returning the size of the accounts HashMap.
    public Integer getNumberOfRegisteredUsers() {
        return accounts.size();
    }

    // Delete Account
    // This method is responsible for deleting a specific account.
    public void deleteAccount(String phoneNumber) {
        accounts.remove(phoneNumber);
    }

    // Delete All Accounts
    // This method is responsible for deleting all accounts.
    public void deleteAll() {
        accounts.clear();
    }

}
