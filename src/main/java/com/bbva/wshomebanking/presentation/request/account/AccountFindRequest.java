package com.bbva.wshomebanking.presentation.request.account;

import lombok.Getter;

@Getter
public class AccountFindRequest {
    private int accountId;
    private int clientId;
}
