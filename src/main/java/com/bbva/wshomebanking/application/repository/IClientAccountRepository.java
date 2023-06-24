package com.bbva.wshomebanking.application.repository;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountId;
import com.bbva.wshomebanking.utilities.exceptions.AccountNotFoundException;
import com.bbva.wshomebanking.utilities.exceptions.ClientNotFoundException;
import com.bbva.wshomebanking.utilities.exceptions.RelationshipNotCreatedException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IClientAccountRepository {

    ClientAccount relateClientToAccount(int clientId, int accountId, String holderType) throws ClientNotFoundException, AccountNotFoundException, RelationshipNotCreatedException;

    ClientAccount get(int clientId, int account);

}
