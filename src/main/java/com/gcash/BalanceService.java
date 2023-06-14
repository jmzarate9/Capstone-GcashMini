package com.gcash;

public class BalanceService {
    private AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public BalanceService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
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
            var newCreditTransaction = new Transaction(amount, Transaction.TransactionType.CREDIT);
            transactionRepository.addTransaction(phoneNumber, newCreditTransaction);
        }
        else {
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
            var newDebitTransaction = new Transaction(amount, Transaction.TransactionType.DEBIT);
            transactionRepository.addTransaction(phoneNumber, newDebitTransaction);
        }
        else {
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
                senderAccount.setBalance(senderAccount.getBalance() - amount);
                receiverAccount.setBalance(senderAccount.getBalance() + amount);
                var newTransferTransaction = new Transaction(amount, Transaction.TransactionType.TRANSFER);
                transactionRepository.addTransaction(receiverPhoneNumber, newTransferTransaction);
            }
        } else {
            throw new AccountNotFoundException("Account " + "not found");
        }
    }
}

