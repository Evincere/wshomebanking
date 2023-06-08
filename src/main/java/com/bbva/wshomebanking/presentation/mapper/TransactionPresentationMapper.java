package com.bbva.wshomebanking.presentation.mapper;

import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.Transaction;
import com.bbva.wshomebanking.presentation.request.client.ClientCreateRequest;
import com.bbva.wshomebanking.presentation.request.transaction.TransactionCreateRequest;
import com.bbva.wshomebanking.presentation.response.client.ClientResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionPresentationMapper {

    /*public ClientResponse domainToResponse(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .personalId(client.getPersonalId())
                .firstName(client.getFirstName())
                .lastName(client.getFirstName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .address(client.getAddress())
                .build();
    }*/

    public Transaction requestToDomain(TransactionCreateRequest request) {
        return Transaction.builder()
                .accountToCBU(request.getAccountTo())
                .transactionType(request.getTransactionType())
                .build();
    }

}
