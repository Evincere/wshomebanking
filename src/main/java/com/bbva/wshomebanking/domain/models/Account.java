package com.bbva.wshomebanking.domain.models;

import com.bbva.wshomebanking.domain.models.enums.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    private int id;
    private BigDecimal balance;
    private String currency;
    private boolean active;
    @JsonIgnore
    private List<ClientAccount> clients;

}
