package com.bbva.wshomebanking.domain.models;

import com.bbva.wshomebanking.domain.models.enums.AccountHolderType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientAccount {
    private Client cliente;
    private Account account;
    private String accountHolderType;
}
