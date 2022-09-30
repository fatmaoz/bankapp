package com.bankapp.repository;

import com.bankapp.exception.RecordNotFoundException;
import com.bankapp.model.Account;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;


@Component
public class AccountRepository {

    public static List<Account> accountList = new ArrayList<>();

    public Account save(Account account) {
        accountList.add(account);
        return account;

    }

    public List<Account> findAll() {
        return accountList;
    }

    public Account findById(Long accountId) {
        return accountList.stream().filter(account -> account.getId().equals(accountId)).findAny().orElseThrow(() ->
                new RecordNotFoundException("This account is not in the database"));
    }

    public Account deleteAccount(Account account) {
        accountList.remove(findById(account.getId()));
        accountList.add(account);
        return account;

    }
}
