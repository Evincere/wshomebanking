package com.bbva.wshomebanking.domain.models.transaccion;

import com.bbva.wshomebanking.domain.models.Cuenta;
import com.bbva.wshomebanking.domain.models.transaccion.Transaccion;

import java.time.LocalDate;

public class Transferencia extends Transaccion {
    private Cuenta cuentaDestino;

}
