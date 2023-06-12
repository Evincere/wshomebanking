package com.bbva.wshomebanking.presentation.request.transaction;

import java.math.BigDecimal;

public class DepositRequest {
    private int accountId;
    private int clientId;
    private BigDecimal amount;
}
