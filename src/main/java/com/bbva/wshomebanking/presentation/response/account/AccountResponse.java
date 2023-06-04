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

    private UUID id;
    private long numeroCuenta;
    private BigDecimal saldo;
    private Currency currency;
    private List<Client> clientes;

}
