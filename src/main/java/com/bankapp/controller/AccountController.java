package com.bankapp.controller;


import com.bankapp.enums.AccountType;
import com.bankapp.model.Account;
import com.bankapp.model.Client;
import com.bankapp.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;


@Controller
@RequestMapping("/")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    public String accountList(Model model) {
        model.addAttribute("accountList", accountService.listAllAccount());
        return "account/index";
    }

    @GetMapping("/create-form")
    public String getCreateForm(Model model){
        Account account = new Account();
        model.addAttribute("account",account);
        model.addAttribute("accountTypes", AccountType.values());

        return "account/create-account";

    }
    @PostMapping("/create")
    public String createAccount(@Valid @ModelAttribute("account") Account account, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("accountTypes", AccountType.values());
            return "account/create-account";
        }
        accountService.createNewAccount(account.getBalance(),
                LocalDate.now(),
                account.getAccountType(),
                account.getClient().getId());
        model.addAttribute("accountList",accountService.listAllAccount());

        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id")Long id){
        accountService.deleteAccount(id);
        return "redirect:/index";

    }





}
