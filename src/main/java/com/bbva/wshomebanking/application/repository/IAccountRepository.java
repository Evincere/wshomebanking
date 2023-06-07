package com.bbva.wshomebanking.application.repository;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;

import java.util.Optional;

public interface IAccountRepository {

    Account saveAccount(Account account);
    Optional<Account> findById(int id);

}
