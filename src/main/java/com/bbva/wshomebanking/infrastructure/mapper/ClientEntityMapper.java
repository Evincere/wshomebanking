package com.bbva.wshomebanking.infrastructure.mapper;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.infrastructure.entities.AccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component()
@RequiredArgsConstructor
public class ClientEntityMapper {

    private final ClientAccountEntityMapper clientAccountEntityMapper;

    public ClientEntity domainToEntity(Client client) {

        return ClientEntity.builder()
                .id(client.getId())
                .personalId(client.getPersonalId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .address(client.getAddress())
                .clientAccounts(new ArrayList<>())
                .build();
    }

    public Client entityToDomain(ClientEntity clientEntity) {

        Client client = new Client();
        client.setId(clientEntity.getId());
        client.setPersonalId(clientEntity.getPersonalId());
        client.setFirstName(clientEntity.getFirstName());
        client.setLastName(clientEntity.getLastName());
        client.setEmail(clientEntity.getEmail());
        client.setPhone(clientEntity.getPhone());
        client.setAddress(clientEntity.getAddress());
        List<ClientAccount> clientAccountList = new ArrayList<>();
        for (ClientAccountEntity clientAccountEntity : clientEntity.getClientAccounts()) {
            AccountEntity accountEntity = clientAccountEntity.getAccount();
            Account account = new Account(
                    accountEntity.getId(),
                    accountEntity.getBalance(),
                    accountEntity.getCurrency(),
                    null
            );
            clientAccountList.add(
                    new ClientAccount(client,account,clientAccountEntity.getHolderType())
            );
        }
        client.setAccounts(clientAccountList);
        return client;
    }

}
