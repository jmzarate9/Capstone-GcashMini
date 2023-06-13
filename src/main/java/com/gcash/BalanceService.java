package com.gcash;

public class BalanceService {
    private AccountRepository accountRepository;


    public BalanceService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public Double getBalance(String accountId) throws AccountNotFoundException {
        Account account = accountRepository.getAccount(accountId);
        if (account != null) {
            return account.balance();
        } else {
            throw new AccountNotFoundException("Account " + accountId + " not found");
        }
    }

    public void credit(String accountId, Double amount) throws AccountNotFoundException{
        Account account = accountRepository.getAccount(accountId);
        if (account != null) {
            account.setBalance(account.balance()+amount);
        } else {
            throw new AccountNotFoundException("Account " + accountId + "not found");
        }
    }

    public void debit(String accountId, Double amount) throws InsufficientBalanceException, AccountNotFoundException {
        Account account = accountRepository.getAccount(accountId);
        if (account != null) {
            if (account.balance()<amount){
                throw new InsufficientBalanceException("Insufficient Balance, please try again");
            }
            account.setBalance(account.balance()-amount);
        } else {
            throw new AccountNotFoundException("Account " + accountId + "not found");
        }
    }

    public void transfer(String senderAccountId, String receiverAccountId, Double amount) throws InsufficientBalanceException, AccountNotFoundException {
        Account senderAccount = accountRepository.getAccount(senderAccountId);
        Account receiverAccount = accountRepository.getAccount(receiverAccountId);
        if (senderAccount != null && receiverAccount != null) {
            if(senderAccount.balance()>=amount){
            debit(senderAccountId,amount);
            credit(receiverAccountId, amount);}
            else {
                throw new InsufficientBalanceException("Insufficient Sender Balance");
            }
        } else {
            throw new AccountNotFoundException("Account " + "not found");
        }
    }
}
