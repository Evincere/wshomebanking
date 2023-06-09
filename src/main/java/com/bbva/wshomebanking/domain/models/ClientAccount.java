package com.bbva.wshomebanking.domain.models;

import com.bbva.wshomebanking.domain.models.enums.AccountHolderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClientAccount {
    private Client client;
    private Account account;
    private String accountHolderType;
}
