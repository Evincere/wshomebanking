package com.bbva.wshomebanking.application.usecases.account;

import com.bbva.wshomebanking.domain.models.Account;

import java.util.Optional;

public interface IAccountFindByUseCase {
    Optional<Account> findById(int id);
}
