package com.bbva.wshomebanking.infrastructure.entities;

import com.bbva.wshomebanking.domain.models.enums.AccountHolderType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ClientsAccounts")
public class ClientAccountEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "clientId")
    private ClientEntity client;

    @Id
    @ManyToOne
    @JoinColumn(name = "accountId")
    private AccountEntity account;

    @Column(name = "holderType", columnDefinition = "varchar(50)")
    private String tipoTitular;


    @Column
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumns({
            @JoinColumn(name = "clientId", referencedColumnName = "clientId"),
            @JoinColumn(name = "accountId", referencedColumnName = "accountId")
    })
    private List<TransactionEntity> transactions;



}
