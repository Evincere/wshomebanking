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

    @EmbeddedId
    private ClientAccountId id;

    @Column(name = "holder_type")
    private String holderType;

    // Omitted getters and setters

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name = "client_id")
    private ClientEntity client;



}
