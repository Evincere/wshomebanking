package com.bbva.wshomebanking.infrastructure.entities;

import com.bbva.wshomebanking.domain.models.enums.Currency;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "cuentas")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name="seq", initialValue=10000, allocationSize=5)
public class AccountEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID id;
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @Column
    private long numeroCuenta;
    private BigDecimal saldo;
    private Currency currency;
    @OneToMany(mappedBy = "cuenta")
    private ArrayList<ClientAccountEntity> clientes;

}
