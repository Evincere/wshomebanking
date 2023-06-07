package com.bbva.wshomebanking.infrastructure.repositories;

import com.bbva.wshomebanking.application.repository.IClientAccountRepository;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.infrastructure.entities.AccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountId;
import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import com.bbva.wshomebanking.infrastructure.mapper.AccountEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.ClientAccountEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.ClientEntityMapper;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IClientAccountSpringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClientAccountRepositoryImpl implements IClientAccountRepository {

    public final IClientAccountSpringRepository clientAccountSpringRepository;
    public final ClientEntityMapper clientEntityMapper;
    public final AccountEntityMapper accountEntityMapper;
    public final ClientAccountEntityMapper clientAccountEntityMapper;
    @Override
    public ClientAccount saveClientAccount(Client cliente, Account account, String holderType) {

        ClientEntity clientEntity = clientEntityMapper.domainToEntity(cliente);
        AccountEntity accountEntity = accountEntityMapper.domainToEntity(account);

        ClientAccountId clientAccountId = new ClientAccountId();
        clientAccountId.setAccountId(accountEntity.getId());
        clientAccountId.setClientId(clientEntity.getId());

        ClientAccountEntity clientAccountEntity = new ClientAccountEntity();
        clientAccountEntity.setId(clientAccountId);
        clientAccountEntity.setAccount(accountEntity);
        clientAccountEntity.setClient(clientEntity);
        clientAccountEntity.setHolderType(holderType);

        ClientAccountEntity savedClientAccount = clientAccountSpringRepository.save(clientAccountEntity);

        return clientAccountEntityMapper.entityToDomain(savedClientAccount);

    }
}
