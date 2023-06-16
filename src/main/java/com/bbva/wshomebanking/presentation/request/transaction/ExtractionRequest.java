package com.bbva.wshomebanking.presentation.request.transaction;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ExtractionRequest {
    private int accountId;
    private int clientId;
    private BigDecimal amount;
}
