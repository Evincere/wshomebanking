package com.bbva.wshomebanking.utilities;

import com.bbva.wshomebanking.domain.models.Account;

import java.math.BigDecimal;

public class TransactionResult {
    private String result;
    private String transactionType;
    private BigDecimal amount;
    private Account accountFrom;
    private String accountTo;
}
