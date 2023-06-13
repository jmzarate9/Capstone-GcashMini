package com.gcash;

import java.util.HashMap;

public class AccountRepository {

    private HashMap<String, Account> accounts;

    public AccountRepository() {
        accounts = new HashMap<>();
        initializeAccounts();
    }
    private void initializeAccounts() {
        accounts.put("09175861661", new Account("Bob", 100.0));
        accounts.put("09175861662", new Account("Marley", 100.0));
        accounts.put("09175861663", new Account("Seth", 100.0));
        accounts.put("09175861664", new Account("Ryan", 100.0));
        accounts.put("09175861665", new Account("Fritz", 100.0));
    }

    // UsersRegistration
    public void userRegistration(String phoneNumber, String name, double initialBalance)
            throws NumberMustBeElevenDigitsException, NumberCannotBeEmptyException, NumberAlreadyExistsException, NameCannotBeEmptyException {
        if (phoneNumber.isEmpty()) {
            throw new NumberCannotBeEmptyException("Phone number cannot be empty.");
        } else if (phoneNumber.length() != 11) {
            throw new NumberMustBeElevenDigitsException("Phone number must be eleven digits.");
        }else if (accounts.containsKey(phoneNumber)) {
            throw new NumberAlreadyExistsException("Phone number already exists.");
        }
        if (name.isEmpty()) {
            throw new NameCannotBeEmptyException("Name cannot be empty.");
        }

        Account account = new Account(name, initialBalance);
        accounts.put(phoneNumber, account);
    }

    // Get Account
    public Account getAccount(String phoneNumber) {
        return accounts.get(phoneNumber);
    }

    // Display All Accounts
    public void displayAll() {
        for (String phoneNumber : accounts.keySet()) {
            Account account = accounts.get(phoneNumber);
            System.out.println("Phone number: " + phoneNumber + ", Name: " + account.getName() + ", Balance: " + account.getBalance());
        }
    }

    // Get Number Of Registered Users
    public int getNumberOfRegisteredUsers() {
        return accounts.size();
    }
}