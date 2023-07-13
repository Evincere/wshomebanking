package com.bbva.wshomebanking.presentation.response.account;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class MyAccountsResponse {
    private int id;
    private String currency;
    private BigDecimal balance;
    private String holderType;

}
