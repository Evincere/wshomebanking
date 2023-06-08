package com.bbva.wshomebanking.domain.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    private int id;
    private BigDecimal importe;
    private LocalDate fechaOperacion;
    private String accountToCBU;
    private String transactionType;
    private ClientAccount clientAccount;

}
