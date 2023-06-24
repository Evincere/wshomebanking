package com.bbva.wshomebanking.presentation.mapper;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.presentation.request.client.ClientCreateRequest;
import com.bbva.wshomebanking.presentation.response.client.ClientCreateResponse;
import com.bbva.wshomebanking.presentation.response.client.ClientFindResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientPresentationMapper {

    public ClientCreateResponse domainToResponse(Client client) {

        List<Account> accountList = new ArrayList<>();
        for (ClientAccount clientAccount :
                client.getAccounts()) {
            Account account = clientAccount.getAccount();
            account.setClients(null);
            accountList.add(account);
        }

        return ClientCreateResponse.builder()
                .id(client.getId())
                .personalId(client.getPersonalId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .address(client.getAddress())
                .accounts(accountList)
                .build();
    }

    public Client requestToDomain(ClientCreateRequest request) {
        return Client.builder()
                .personalId(request.getPersonalId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .password(request.getPassword())
                .build();
    }

    public ClientFindResponse findOneToResponse(Client client) {

        List<Account> accountList = new ArrayList<>();
        List<ClientAccount> clientAccountList = client.getAccounts();

        for (ClientAccount clientAccount :
                clientAccountList) {
            Account account = clientAccount.getAccount();
            account.setClients(null);
            accountList.add(account);
        }

        return ClientFindResponse.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .personalId(client.getPersonalId())
                .phone(client.getPhone())
                .email(client.getEmail())
                .address(client.getAddress())
                .accountList(accountList)
                .build();


    }



}
