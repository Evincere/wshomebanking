package com.bbva.wshomebanking.application.services;

import com.bbva.wshomebanking.application.repository.IAccountRepository;
import com.bbva.wshomebanking.application.usecases.account.IAccountCreateUseCase;
import com.bbva.wshomebanking.application.usecases.account.IAccountFindByUseCase;
import com.bbva.wshomebanking.application.usecases.account.IAccountSaveUseCase;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.presentation.mapper.AccountPresentationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountCreateUseCase, IAccountSaveUseCase, IAccountFindByUseCase {

    private final IAccountRepository accountRepository;
    private final AccountPresentationMapper accountMapper;

    @Override
    public Account create(Client client, String currency) {
        //return accountMapper.clientAndCurrencyToDomain(client, currency);
        return null;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.saveAccount(account);
    }

    @Override
    public Optional<Account> findById(int id) {
        return accountRepository.findById(id);
    }
}
