package com.bbva.wshomebanking.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Account account;
    private Client cliente;
    private BigDecimal importe;
    private LocalDate fechaOperacion;

}
