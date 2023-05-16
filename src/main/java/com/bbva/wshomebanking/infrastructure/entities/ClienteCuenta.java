package com.bbva.wshomebanking.infrastructure.entities;

import com.bbva.wshomebanking.infrastructure.enums.TipoTitular;
import jakarta.persistence.*;

@Entity
@Table(name = "cliente_cuenta")
public class ClienteCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;

    @Column(name = "tipo_titular", columnDefinition = "varchar(50)")
    private TipoTitular tipoTitular;


}
