package com.bbva.wshomebanking.presentation.response.account;

import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.enums.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Builder
@Getter
@Setter
public class AccountResponse {
    private int accountNumber;
    private BigDecimal balance;
    private String currency;
    private List<Client> clients;

}
