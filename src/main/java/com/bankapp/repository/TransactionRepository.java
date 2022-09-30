package com.bankapp.repository;

import com.bankapp.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionRepository {

    public List<Transaction> transactionList = new ArrayList<>();

    public Transaction save(Transaction transaction){
        transactionList.add(transaction);
        return transaction;
    }

    public List<Transaction> findAll() {
        return  transactionList;
    }

    public List<Transaction> retrieveLastTransactions() {
        return transactionList.stream().
                sorted(Comparator.comparing(Transaction::getCreationDate)).limit(10).collect(Collectors.toList());
    }

    public List<Transaction> findTransactionListById(Long id) {
        return transactionList.stream().filter(transaction -> transaction.getSender().equals(id) || transaction.getReceiver().equals(id))
                .collect(Collectors.toList());
    }
}
