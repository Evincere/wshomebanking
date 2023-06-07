package com.bbva.wshomebanking.application.usecases.account;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;

public interface IAccountCreateUseCase {

    public Account create(Client client, String currency);

}
