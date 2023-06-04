package com.bbva.wshomebanking.infrastructure.repositories;

import com.bbva.wshomebanking.application.repository.IAccountRepository;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.infrastructure.entities.AccountEntity;
import com.bbva.wshomebanking.infrastructure.mapper.AccountEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.ClientEntityMapper;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IAccountSpringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements IAccountRepository {

    private final AccountEntityMapper accountEntityMapper;
    private final IAccountSpringRepository accountSpringRepository;

    @Override
    public Account saveAccount(Account account) {
        AccountEntity accountEntity = accountEntityMapper.domainToEntity(account);

        accountSpringRepository.save(accountEntity);

        return accountEntityMapper.entityToDomain(accountEntity);
    }
}
