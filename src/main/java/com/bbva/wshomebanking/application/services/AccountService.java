package com.bbva.wshomebanking.application.services;

import com.bbva.wshomebanking.application.repository.IAccountRepository;
import com.bbva.wshomebanking.application.usecases.account.IAccountCreateUseCase;
import com.bbva.wshomebanking.application.usecases.account.IAccountFindByUseCase;
import com.bbva.wshomebanking.application.usecases.client.IClientFindByUseCase;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.presentation.mapper.AccountPresentationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountCreateUseCase, IAccountFindByUseCase {

    private final IAccountRepository accountRepository;
    private final IClientFindByUseCase clientFindByUseCase;
    private final AccountPresentationMapper accountMapper;

    @Override
    public Account create(int clientId,String currency) {
        // get the client
        Client client = clientFindByUseCase.findById(clientId).get();

        // create the account domain object
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        account.setCurrency(currency);

        return accountRepository.create(account, client);
    }

    @Override
    public Optional<Account> findById(int id) {
        return accountRepository.findById(id);
    }
}
