package com.bbva.wshomebanking.presentation.response.clientaccount;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ClientAccountResponse {
    String personalId;
    String holderType;
    int accountId;
    String currency;
}
