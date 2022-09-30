package com.bankapp.service.impl;

import com.bankapp.enums.AccountType;
import com.bankapp.exception.AccountOwnerShipException;
import com.bankapp.exception.BadRequestException;
import com.bankapp.exception.BalanceNotSufficientException;
import com.bankapp.exception.UnderConstructionException;
import com.bankapp.model.Account;
import com.bankapp.model.Transaction;
import com.bankapp.repository.AccountRepository;
import com.bankapp.repository.TransactionRepository;
import com.bankapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.List;


@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;

    AccountRepository accountRepository;
    TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction makeTransfer(BigDecimal amount, LocalDate creationDate,
                                    Account sender, Account receiver, String message) {
        if(!underConstruction){
        checkAccountOwnerShip(sender, receiver);
        validateAccounts(sender, receiver);
        executeBalanceAndUpdateIfRequired(amount, sender, receiver);
        return transactionRepository.save(new Transaction(sender,receiver,amount,message,creationDate)) ;
        }
        else {
            throw new UnderConstructionException("Make transfer is not possible for now. Please try again later");
        }

    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) {
        if(checkSenderBalance(sender, amount)){
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        } else {
            throw new BalanceNotSufficientException("Balance is not enough for this transaction");
        }
    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) > 0;

    }

    private void validateAccounts(Account sender, Account receiver) {
        if(sender == null || receiver == null){
            throw new BadRequestException("Sender or receiver can not be null");
        }
        if(sender.getId().equals(receiver.getId())){
            throw new BadRequestException("Sender account needs to be different from recaiver account");
        }

        findAccountById(sender.getId());
        findAccountById(receiver.getId());
    }

    private Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    private void checkAccountOwnerShip(Account sender, Account receiver) {
        if((sender.getAccountType().equals(AccountType.SAVINGS) || receiver.getAccountType().equals(AccountType.SAVINGS))
        && !sender.getClient().getId().equals(receiver.getClient().getId())){
            throw new AccountOwnerShipException("When one of the account type is SAVINGS, sender and receiver has to be same person");
        }
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> retrieveLastTransaction() {
        return transactionRepository.retrieveLastTransactions();
    }

    @Override
    public List<Transaction> findTransactionListById(Long id) {
        return transactionRepository.findTransactionListById(id);
    }
}
