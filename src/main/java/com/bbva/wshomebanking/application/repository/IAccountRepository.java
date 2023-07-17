package com.bbva.wshomebanking.application.repository;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.infrastructure.entities.AccountEntity;
import com.bbva.wshomebanking.utilities.exceptions.ErrorWhenSavingException;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository {

    Account create(Account account, Client client);
    Optional<Account> findById(int id);
    List<Account> findAll();
    Account updateAccount(Account account) throws ErrorWhenSavingException;


}
