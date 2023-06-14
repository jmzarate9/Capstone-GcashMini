package com.gcash;

public class BalanceService {
    private AccountRepository accountRepository;


    public BalanceService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public Double getBalance(String phoneNumber) throws AccountNotFoundException {
        Account account = accountRepository.getAccount(phoneNumber);
        if (account != null) {
            return account.getBalance();
        } else {
            throw new AccountNotFoundException("Account " + phoneNumber + " not found");
        }
    }

    public void credit(String phoneNumber, Double amount) throws AccountNotFoundException{
        Account account = accountRepository.getAccount(phoneNumber);
        if (account != null) {
            account.setBalance(account.getBalance()+amount);
        } else {
            throw new AccountNotFoundException("Account " + phoneNumber + "not found");
        }
    }

    public void debit(String phoneNumber, Double amount) throws InsufficientBalanceException, AccountNotFoundException {
        Account account = accountRepository.getAccount(phoneNumber);
        if (account != null) {
            if (account.getBalance()<amount){
                throw new InsufficientBalanceException("Insufficient Balance");
            }
            account.setBalance(account.getBalance()-amount);
        } else {
            throw new AccountNotFoundException("Account " + phoneNumber + "not found");
        }
    }

    public void transfer(String senderPhoneNumber, String receiverPhoneNumber, Double amount) throws InsufficientBalanceException, AccountNotFoundException {
        Account senderAccount = accountRepository.getAccount(senderPhoneNumber);
        Account receiverAccount = accountRepository.getAccount(receiverPhoneNumber);
        if (senderAccount != null && receiverAccount != null) {
            if(senderAccount.getBalance()<amount)
                throw new InsufficientBalanceException("Insufficient Sender Balance");
            else {
                    debit(senderPhoneNumber,amount);
                    credit(receiverPhoneNumber, amount);
            }
        } else {
            throw new AccountNotFoundException("Account " + "not found");
        }
    }
}
