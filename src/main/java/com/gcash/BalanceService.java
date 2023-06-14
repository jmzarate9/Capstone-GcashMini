package com.gcash;

public class BalanceService {

    //allows calling of methods in the accountRepository and transactionRepository Class method
    private AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public BalanceService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    //getBalance using phone number using accountRepository.getAccount()

    public Double getBalance(String phoneNumber) throws AccountNotFoundException {
        Account account = accountRepository.getAccount(phoneNumber);
        if (account != null) {
            return account.getBalance();
    // outputs Account not found if phone number is not found on the AccountRepository
        } else {
            throw new AccountNotFoundException("Account " + phoneNumber + " not found");
        }
    }

    //outputs a credit function that adds to the balance of the inputted phone number
    public void credit(String phoneNumber, Double amount) throws AccountNotFoundException{
        Account account = accountRepository.getAccount(phoneNumber);
        if (account != null) {
            account.setBalance(account.getBalance()+amount);
            //Transaction is stored in the transactionRepository
            var newCreditTransaction = new Transaction(amount, Transaction.TransactionType.CREDIT);
            transactionRepository.addTransaction(phoneNumber, newCreditTransaction);
        }
        else {
            // outputs Account not found if phone number is not found on the AccountRepository
            throw new AccountNotFoundException("Account " + phoneNumber + "not found");
        }
    }

    //outputs a debit function that subtracts to the balance of the inputted phone number
    public void debit(String phoneNumber, Double amount) throws InsufficientBalanceException, AccountNotFoundException {
        Account account = accountRepository.getAccount(phoneNumber);
        if (account != null) {
            if (account.getBalance()<amount){
                //When balance is less than the amount to be debited, throws an Insufficient Balance message
                throw new InsufficientBalanceException("Insufficient Balance");
            }
            account.setBalance(account.getBalance()-amount);
            //Transaction is stored in the transactionRepository
            var newDebitTransaction = new Transaction(amount, Transaction.TransactionType.DEBIT);
            transactionRepository.addTransaction(phoneNumber, newDebitTransaction);
        }
        else {
            throw new AccountNotFoundException("Account " + phoneNumber + "not found");
        }
    }

    //method used for transferring the desired "amount" from sender to the recipient with the phone numbers as the user ID
    public void transfer(String senderPhoneNumber, String receiverPhoneNumber, Double amount) throws InsufficientBalanceException, AccountNotFoundException {
        Account senderAccount = accountRepository.getAccount(senderPhoneNumber);
        Account receiverAccount = accountRepository.getAccount(receiverPhoneNumber);
        if (senderAccount != null && receiverAccount != null) {
            if(senderAccount.getBalance()<amount)
                //When balance of receiver is less than the amount to be debited, throw an Insufficient Balance message
                throw new InsufficientBalanceException("Insufficient Sender Balance");
            else {
                senderAccount.setBalance(senderAccount.getBalance() - amount);
                receiverAccount.setBalance(senderAccount.getBalance() + amount);
                //Transaction is stored in the transactionRepository
                var newTransferTransaction = new Transaction(amount, Transaction.TransactionType.TRANSFER);
                transactionRepository.addTransaction(receiverPhoneNumber, newTransferTransaction);
            }
        } else {
            // outputs Account not found if phone number is not found on the AccountRepository
            throw new AccountNotFoundException("Account " + "not found");
        }
    }
}

