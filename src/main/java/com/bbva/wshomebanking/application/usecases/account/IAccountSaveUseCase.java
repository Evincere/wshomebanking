package com.bbva.wshomebanking.application.usecases.account;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;

public interface IAccountSaveUseCase {

    Account create(Account account, Client client);

}
