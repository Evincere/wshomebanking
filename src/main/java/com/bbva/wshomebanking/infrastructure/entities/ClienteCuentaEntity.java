package com.bbva.wshomebanking.infrastructure.entities;

import com.bbva.wshomebanking.domain.models.enums.TipoTitular;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "clientes_cuentas")
public class ClienteCuentaEntity {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/


    @Id
    @ManyToOne
    @JoinColumn(name = "clienteId")
    private ClienteEntity cliente;

    @Id
    @ManyToOne
    @JoinColumn(name = "cuentaId")
    private CuentaEntity cuenta;

    @Column(name = "tipo_titular", columnDefinition = "varchar(50)")
    private TipoTitular tipoTitular;



}
