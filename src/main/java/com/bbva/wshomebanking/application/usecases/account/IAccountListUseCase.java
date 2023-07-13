package com.bbva.wshomebanking.application.usecases.account;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;

import java.util.List;

public interface IAccountListUseCase {
    List<Account> getAccountList();
}
