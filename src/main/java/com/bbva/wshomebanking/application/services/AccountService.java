package com.bbva.wshomebanking.application.services;

import com.bbva.wshomebanking.application.repository.IAccountRepository;
import com.bbva.wshomebanking.application.usecases.account.IAccountCreateUseCase;
import com.bbva.wshomebanking.application.usecases.account.IAccountSaveUseCase;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.presentation.mapper.AccountPresentationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountCreateUseCase, IAccountSaveUseCase {

    private final IAccountRepository accountRepository;
    private final AccountPresentationMapper accountMapper;

    @Override
    public Account create(Client client, String currency) {
        return accountMapper.clientAndCurrencyToDomain(client, currency);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.saveAccount(account);
    }
}
