package com.bbva.wshomebanking.infrastructure.mapper;

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

    //private final ClientEntityMapper clientEntityMapper;
    //private final AccountEntityMapper accountEntityMapper;
    public ClientAccount entityToDomain(ClientAccountEntity clientAccountEntity) {
        /*
        ClientAccount clientAccount = new ClientAccount();

        clientAccount.setClient(clientAccountEntity.entityToDomain(clientAccountEntity.getClient()));
        clientAccount.setAccount(accountEntityMapper.entityToDomain(clientAccountEntity.getAccount()));
        clientAccount.setAccountHolderType(clientAccountEntity.getHolderType());

        return clientAccount;
        */

         return null;
    }

    public ClientAccountEntity domainToEntity(ClientAccount clientAccount) {
        /*return ClientAccountEntity.builder()
                .id(new ClientAccountId(clientAccount.getAccount().))*/
        return null;
    }

}
