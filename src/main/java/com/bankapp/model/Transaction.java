package com.bankapp.model;

import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @NotNull
    private Account sender;
    @NotNull
    private Account receiver;
    @NotNull
    @Positive
    private BigDecimal amount;
    @NotEmpty
    @Size(min=2, max = 250)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String message;
    private LocalDate creationDate;

}
