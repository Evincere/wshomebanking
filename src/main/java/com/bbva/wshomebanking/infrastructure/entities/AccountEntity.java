package com.bbva.wshomebanking.infrastructure.entities;

import com.bbva.wshomebanking.domain.models.enums.Currency;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Accounts")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private BigDecimal balance;
    private String currency;
    private boolean active;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<ClientAccountEntity> clientAccounts;

}
