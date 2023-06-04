package com.bbva.wshomebanking.infrastructure.mapper;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.infrastructure.entities.AccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component()
@RequiredArgsConstructor
public class AccountEntityMapper {

    public AccountEntity domainToEntity(Account account) {
        return AccountEntity.builder()
                .id(account.getId())
                .saldo(account.getSaldo())
                .currency(account.getCurrency())
                .build();
    }

    public Account entityToDomain(AccountEntity accountEntity) {
        Account account = new Account();
        account.setId(account.getId());
        account.setNumeroCuenta(accountEntity.getNumeroCuenta());
        account.setCurrency(accountEntity.getCurrency());
        account.setSaldo(accountEntity.getSaldo());

        return account;
    }

}