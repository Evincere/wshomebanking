package com.bbva.wshomebanking.infrastructure.repositories;

import com.bbva.wshomebanking.application.repository.IAccountRepository;
import com.bbva.wshomebanking.application.repository.IClientAccountRepository;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.infrastructure.entities.AccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountId;
import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import com.bbva.wshomebanking.infrastructure.mapper.AccountEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.ClientEntityMapper;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IAccountSpringRepository;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IClientAccountSpringRepository;
import com.bbva.wshomebanking.utilities.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements IAccountRepository {

    private final AccountEntityMapper accountEntityMapper;
    private final ClientEntityMapper clientEntityMapper;
    private final IAccountSpringRepository accountSpringRepository;
    public final IClientAccountSpringRepository clientAccountSpringRepository;

    @Override
    public Account create(Account account, Client client) {
        // parse the domain objects
        ClientEntity clientEntity = clientEntityMapper.domainToEntity(client);
        AccountEntity accountEntity = accountEntityMapper.domainToEntity(account);
        List<ClientAccountEntity> clientAccountEntityList = new ArrayList<>();
        // persist the account
        accountEntity = accountSpringRepository.save(accountEntity);
        // create the relationship entity
        ClientAccountId clientAccountId = new ClientAccountId(accountEntity.getId(),clientEntity.getId());
        ClientAccountEntity clientAccountEntity = new ClientAccountEntity();
        clientAccountEntity.setId(clientAccountId);
        clientAccountEntity.setAccount(accountEntity);
        clientAccountEntity.setClient(clientEntity);
        clientAccountEntity.setHolderType(AppConstants.getDefaultHolderType());
        // persist the relationship
        clientAccountEntity = clientAccountSpringRepository.save(clientAccountEntity);
        clientAccountEntityList.add(clientAccountEntity);
        accountEntity.setClientAccounts(clientAccountEntityList);

        return accountEntityMapper.entityToDomain(accountEntity);
    }

    @Override
    public Optional<Account> findById(int id) {
        Optional<AccountEntity> optionalAccount = accountSpringRepository.findById(Integer.valueOf(id));
        if (optionalAccount.isEmpty()) {
            return Optional.empty();
        }

        return optionalAccount.map(accountEntityMapper::entityToDomain);
    }
}
