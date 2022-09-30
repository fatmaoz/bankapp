package com.bankapp.service;

import com.bankapp.model.Account;
import com.bankapp.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    Transaction makeTransfer(BigDecimal amount, LocalDate creationDate,
                             Account sender, Account receiver,
                             String message);

    List<Transaction> findAll();

   List<Transaction> retrieveLastTransaction();

    List<Transaction> findTransactionListById(Long id);
}
