package com.bbva.wshomebanking.domain.models;

import com.bbva.wshomebanking.domain.models.enums.Currency;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    private UUID id;
    private long numeroCuenta;
    private BigDecimal saldo;
    private Currency currency;
    private List<Client> clientes;

}
