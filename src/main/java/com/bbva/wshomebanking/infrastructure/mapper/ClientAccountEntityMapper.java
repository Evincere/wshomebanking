package com.bbva.wshomebanking.infrastructure.mapper;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountId;
import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component()
@RequiredArgsConstructor
public class ClientAccountEntityMapper {

    public ClientAccount entityToDomain(ClientAccountEntity clientAccountEntity) {
        ClientAccount clientAccount = new ClientAccount();

        clientAccount.setClient(new Client(
                clientAccountEntity.getClient().getId(),
                clientAccountEntity.getClient().getPersonalId(),
                clientAccountEntity.getClient().getFirstName(),
                clientAccountEntity.getClient().getLastName(),
                clientAccountEntity.getClient().getEmail(),
                clientAccountEntity.getClient().getAddress(),
                clientAccountEntity.getClient().getPhone(),
                null,
                null,
                null
        ));
        clientAccount.setAccount(new Account(
                clientAccountEntity.getAccount().getId(),
                clientAccountEntity.getAccount().getBalance(),
                clientAccountEntity.getAccount().getCurrency(),
                null
        ));
        clientAccount.setAccountHolderType(clientAccountEntity.getHolderType());

        return clientAccount;

    }

    public ClientAccountEntity domainToEntity(ClientAccount clientAccount) {
        /*return ClientAccountEntity.builder()
                .id(new ClientAccountId(clientAccount.getAccount().))*/
        return null;
    }

}
