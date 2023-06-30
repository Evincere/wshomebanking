package com.bbva.wshomebanking.infrastructure.entities;

import com.bbva.wshomebanking.domain.models.ClientAccount;
import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "Transactions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "account_to")
    private String accountTo;

    @Column(name = "amount")
    private BigDecimal amount;

    // Omitted getters and setters

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "account_id", referencedColumnName = "account_id", insertable = false, updatable = false),
            @JoinColumn(name = "client_id", referencedColumnName = "client_id", insertable = false, updatable = false)
    })
    private ClientAccountEntity clientAccount;

}
