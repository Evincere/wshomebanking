package com.bbva.wshomebanking.application.usecases.client;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;

public interface IClientSaveUseCase {

    Client save(Client client, Account account);

}
