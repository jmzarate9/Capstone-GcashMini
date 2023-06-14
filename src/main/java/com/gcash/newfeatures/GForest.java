package com.gcash.newfeatures;
import com.gcash.*;

import java.util.ArrayList;
import java.util.List;

public class GForest {
     private int energy;
     private String id;
     //private TransactionRepository transactionRepository;
    private List<Integer> transactionCount = new ArrayList<>();

    public GForest(String id, TransactionRepository transactionRepository){
        this.id = id;
        //this.transactionRepository = transactionRepository;
        int size = transactionRepository.getUserTransactions(id).size();
        this.energy = size * 150; // get 150 energy for each transaction
        this.transactionCount.add(size);
    }
    public Boolean plantATree(TransactionRepository transactionRepository) {
        updateEnergy(transactionRepository);
        //
        if (this.energy >= 1500) {
            this.energy -= 1500;
            System.out.println("Congratulations! You have planted a tree. Your remaining balance is " + this.energy + " energy.");
            return true;
        } else {
            // throw exception
            System.out.println("Not enough energy to plant a tree. Your current balance is " + this.energy + " energy.");

        }
        return false;
    }

    public void updateEnergy(TransactionRepository transactionRepository) {
        int latestTransactionNumber = this.transactionCount.get(this.transactionCount.size() - 1);
        int diff = transactionRepository.getUserTransactions(this.id).size() - latestTransactionNumber;
        this.energy += diff * 150;
        this.transactionCount.add(latestTransactionNumber + diff);
    }

    public int getEnergy(){
        return this.energy;
    }
}
