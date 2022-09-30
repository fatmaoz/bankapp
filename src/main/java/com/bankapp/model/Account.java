package com.bankapp.model;


import com.bankapp.enums.AccountStatus;
import com.bankapp.enums.AccountType;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Long id;

    @NotNull
    @Positive
    private BigDecimal balance;
    private AccountStatus accountStatus;
    @NotNull
    private AccountType accountType;
    private LocalDate creationDate;
    @NotNull
    private Client client;

}
