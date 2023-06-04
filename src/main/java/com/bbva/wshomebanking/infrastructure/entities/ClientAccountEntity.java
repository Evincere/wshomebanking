package com.bbva.wshomebanking.infrastructure.entities;

import com.bbva.wshomebanking.domain.models.enums.AccountHolderType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clientes_cuentas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientAccountEntity {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/


    @Id
    @ManyToOne
    @JoinColumn(name = "clienteId")
    private ClientEntity cliente;

    @Id
    @ManyToOne
    @JoinColumn(name = "cuentaId")
    private AccountEntity cuenta;

    @Column(name = "tipo_titular", columnDefinition = "varchar(50)")
    private AccountHolderType accountHolderType;



}
