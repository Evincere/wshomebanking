package com.bbva.wshomebanking.domain.models;

import com.bbva.wshomebanking.domain.models.enums.AccountHolderType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientAccount {
    private Client client;
    private Account account;
    private String accountHolderType;
}
