package com.bbva.wshomebanking.domain.models;

import com.bbva.wshomebanking.domain.models.enums.Moneda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta {
    private int numeroCuenta;
    private BigDecimal saldo;
    private Moneda moneda;
    private List<Cliente> clientes;

}
