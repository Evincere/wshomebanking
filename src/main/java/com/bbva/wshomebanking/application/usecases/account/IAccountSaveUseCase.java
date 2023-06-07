package com.bbva.wshomebanking.application.usecases.account;

import com.bbva.wshomebanking.domain.models.Account;

public interface IAccountSaveUseCase {

    Account save(Account account);

}
