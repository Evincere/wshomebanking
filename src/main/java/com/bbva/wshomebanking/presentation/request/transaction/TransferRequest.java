package com.bbva.wshomebanking.presentation.request.transaction;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequest {
    private int accountId;
    private int clientId;
    private BigDecimal amount;
    private String cbu;
}
