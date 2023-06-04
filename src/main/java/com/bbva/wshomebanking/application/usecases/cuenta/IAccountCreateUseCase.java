package com.bbva.wshomebanking.application.usecases.cuenta;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.enums.Currency;

public interface IAccountCreateUseCase {

    public Account create(Client client, Currency currency);

}
