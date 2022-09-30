package com.bankapp.service;

import com.bankapp.enums.AccountType;
import com.bankapp.model.Account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    Account createNewAccount(BigDecimal balance, LocalDate creationDate, AccountType accountType, Long userId);

    List<Account> listAllAccount();

    void deleteAccount(Long account);

    Account retrieveById(Long account);
}
