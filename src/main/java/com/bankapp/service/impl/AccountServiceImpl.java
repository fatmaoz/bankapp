package com.bankapp.service.impl;

import com.bankapp.enums.AccountStatus;
import com.bankapp.enums.AccountType;
import com.bankapp.model.Account;
import com.bankapp.model.Client;
import com.bankapp.repository.AccountRepository;
import com.bankapp.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;



@Component
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

//TO DO :  Nasil unique id uretecegiz
    @Override
    public Account createNewAccount(BigDecimal balance, LocalDate creationDate, AccountType accountType, Long userId) {
        Account account = new Account(new Random().nextLong(),balance,AccountStatus.ACTIVE,accountType,creationDate,new Client());
//                Account.builder().id(UUID.randomUUID())
//                .userId(userId).accountType(accountType).balance(balance).
//                creationDate(creationDate).accountStatus(AccountStatus.ACTIVE).build();
        return accountRepository.save(account);
    }

    @Override
    public List<Account> listAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId);
        account.setAccountStatus(AccountStatus.DELETED);
        accountRepository.deleteAccount(account);

    }

    @Override
    public Account retrieveById(Long account) {
        return accountRepository.findById(account);
    }
}
