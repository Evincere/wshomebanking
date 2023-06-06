package com.bbva.wshomebanking.infrastructure.repositories;

import com.bbva.wshomebanking.application.repository.IAccountRepository;
import com.bbva.wshomebanking.application.repository.IClientAccountRepository;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.infrastructure.entities.AccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountId;
import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import com.bbva.wshomebanking.infrastructure.mapper.AccountEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.ClientEntityMapper;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IAccountSpringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements IAccountRepository {

    private final AccountEntityMapper accountEntityMapper;
    private final ClientEntityMapper clientEntityMapper;
    private final IAccountSpringRepository accountSpringRepository;
    private final IClientAccountRepository clientAccountRepository;

    @Override
    public Account saveAccount(Account account) {
        AccountEntity accountEntity = accountEntityMapper.domainToEntity(account);
        ClientEntity clientEntity = clientEntityMapper.domainToEntity(account.getClients().get(0));
        ClientAccountEntity clientAccountEntity = new ClientAccountEntity();
        clientAccountEntity.setAccount(accountEntity);
        clientAccountEntity.setClient(clientEntity);
        clientAccountEntity.setId(new ClientAccountId());
        clientAccountEntity.setHolderType("TITULAR");

        accountEntity = accountSpringRepository.save(accountEntity);
        clientAccountRepository.save(clientAccountEntity);


        return accountEntityMapper.entityToDomain(accountEntity);
    }
}
