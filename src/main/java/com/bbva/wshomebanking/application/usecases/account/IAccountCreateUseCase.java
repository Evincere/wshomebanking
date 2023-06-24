package com.bbva.wshomebanking.application.usecases.account;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.utilities.exceptions.RecordNotFoundException;

public interface IAccountCreateUseCase {

    public Account create(int clientId, String currency) throws RecordNotFoundException;

}
