package com.bbva.wshomebanking.infrastructure.entities;

import com.bbva.wshomebanking.domain.models.ClientAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "Transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "account_id")
    private int accountId;

    @Column(name = "client_id")
    private int clientId;

    @Column(name = "transaction_type")
    private String transactionType;

    // Omitted getters and setters

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "account_id", referencedColumnName = "account_id", insertable = false, updatable = false),
            @JoinColumn(name = "client_id", referencedColumnName = "client_id", insertable = false, updatable = false)
    })
    private ClientAccountEntity clientAccount;

}
