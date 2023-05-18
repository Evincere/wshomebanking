package com.bbva.wshomebanking.domain.models.transaccion;

import com.bbva.wshomebanking.domain.models.Cliente;
import com.bbva.wshomebanking.domain.models.Cuenta;
import com.bbva.wshomebanking.domain.models.enums.TipoTransaccion;
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
public class Transaccion {
    private Cuenta cuenta;
    private Cliente cliente;
    private BigDecimal importe;
    private LocalDate fechaOperacion;

}
