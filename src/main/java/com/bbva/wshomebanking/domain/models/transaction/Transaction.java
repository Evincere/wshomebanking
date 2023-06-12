package com.bbva.wshomebanking.domain.models.transaction;

import com.bbva.wshomebanking.domain.models.ClientAccount;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public abstract class Transaction {
    private int id;
    private String transactionType;
    private ClientAccount accountFrom;
    private String alias;
    private BigDecimal amount;
    public abstract void validateTransaction();
    public abstract void applyFundsMovements();

}
