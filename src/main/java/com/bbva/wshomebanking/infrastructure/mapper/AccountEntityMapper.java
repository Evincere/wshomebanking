package com.bbva.wshomebanking.infrastructure.mapper;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.infrastructure.entities.AccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import com.bbva.wshomebanking.presentation.mapper.AccountPresentationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component()
@RequiredArgsConstructor
public class AccountEntityMapper {

    public AccountEntity domainToEntity(Account account) {

        return AccountEntity.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .active(account.isActive())
                .clientAccounts(new ArrayList<>())
                .build();
    }

    public Account entityToDomain(AccountEntity accountEntity) {
        Account account = new Account();
        account.setId(accountEntity.getId());
        account.setCurrency(accountEntity.getCurrency());
        account.setBalance(accountEntity.getBalance());
        List<ClientAccount> clientAccountList = new ArrayList<>();
        for (ClientAccountEntity clientAccountEntity : accountEntity.getClientAccounts()) {
            ClientEntity clientEntity = clientAccountEntity.getClient();
            Client client = new Client(
                    clientEntity.getId(),
                    clientEntity.getPersonalId(),
                    clientEntity.getFirstName(),
                    clientEntity.getLastName(),
                    clientEntity.getEmail(),
                    clientEntity.getAddress(),
                    clientEntity.getPhone(),
                    clientEntity.isActive(),
                    clientEntity.getPassword(),
                    clientEntity.getSalt(),
                    null
            );
            clientAccountList.add(
                    new ClientAccount(client,account,clientAccountEntity.getHolderType())
            );
        }
        account.setClients(clientAccountList);

        return account;
    }

}
