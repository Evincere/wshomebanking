package com.bbva.wshomebanking.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "transacciones")
public class TransaccionEntity {

    /*@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID id;*/
    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "clienteId", referencedColumnName = "clienteId"),
            @JoinColumn(name = "cuentaId", referencedColumnName = "cuentaId")
    })
    private ClienteCuentaEntity clienteCuentaEntity;

    private String tipo;
    private BigDecimal monto;


}
