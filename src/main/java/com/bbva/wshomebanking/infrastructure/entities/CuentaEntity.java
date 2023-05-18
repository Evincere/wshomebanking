package com.bbva.wshomebanking.infrastructure.entities;

import com.bbva.wshomebanking.domain.models.enums.Moneda;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "cuentas")
public class CuentaEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID id;
    @GeneratedValue(generator = "GenerationType.IDENTITY")
    private int numeroCuenta;
    private BigDecimal saldo;
    private Moneda moneda;
    @OneToMany(mappedBy = "cuenta")
    private ArrayList<ClienteCuentaEntity> clientes;

}
