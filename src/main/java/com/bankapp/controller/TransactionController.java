package com.bankapp.controller;

import com.bankapp.model.Account;
import com.bankapp.model.Transaction;
import com.bankapp.service.AccountService;
import com.bankapp.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;


@Controller
public class TransactionController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public TransactionController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }


    @GetMapping("/make-transfer")
    public String retrieveTransactionForm(Model model){
        model.addAttribute("accounts", accountService.listAllAccount());
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("lastTransactionList", transactionService.retrieveLastTransaction());

        return "transaction/make-transfer";

    }


    @PostMapping("/transfer")
    public String makeTransfer(@Valid @ModelAttribute("transaction")Transaction transaction, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("accounts", accountService.listAllAccount());
            return "transaction/make-transfer";
        }

        Account reciever = accountService.retrieveById(transaction.getReceiver().getId());
        Account sender = accountService.retrieveById(transaction.getSender().getId());
        transactionService.makeTransfer(transaction.getAmount(),LocalDate.now(),sender,reciever,transaction.getMessage());
        return "redirect:/make-transfer";

    }

    @GetMapping("/transaction/{id}")
    public String transactionDetailById(@PathVariable("id")Long id, Model model) {

        model.addAttribute("transactionList", transactionService.findTransactionListById(id));

        return "transaction/transactions";
    }
}
