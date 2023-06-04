package com.bbva.wshomebanking.infrastructure.entities;

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
    private long transactionId;

    @Id
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "clientId", referencedColumnName = "clientId"),
            @JoinColumn(name = "accountId", referencedColumnName = "accountId")
    })
    private ClientAccountEntity clientAccountEntity;

    private String type;
    private BigDecimal amount;


}
