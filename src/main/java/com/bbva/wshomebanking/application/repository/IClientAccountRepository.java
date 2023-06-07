package com.bbva.wshomebanking.application.repository;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IClientAccountRepository {

    ClientAccount saveClientAccount(Client cliente, Account account, String holderType);

}
