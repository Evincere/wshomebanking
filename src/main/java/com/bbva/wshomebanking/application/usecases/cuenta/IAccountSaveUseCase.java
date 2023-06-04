package com.bbva.wshomebanking.application.usecases.cuenta;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;

public interface IAccountSaveUseCase {

    Account save(Account account);

}
