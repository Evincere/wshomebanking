package com.bbva.wshomebanking.infrastructure.entities;

import com.bbva.wshomebanking.infrastructure.enums.Moneda;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@Table(name = "cuenta")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fechaCreacion;
    private BigDecimal saldoActual;
    private BigDecimal descubiertoAcordado;
    private LocalDate fechaCierre;
    private Moneda moneda;
    @OneToMany(mappedBy = "cuenta")
    private ArrayList<ClienteCuenta> titulares;

}
