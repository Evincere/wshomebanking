package com.bbva.wshomebanking.presentation.mapper;

import com.bbva.wshomebanking.domain.models.transaction.Transaction;
import com.bbva.wshomebanking.presentation.request.transaction.TransactionCreateRequest;
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
        /*return Transaction.builder()
                .accountTo(request.getAccountTo())
                .transactionType(request.getTransactionType())
                .build();*/return null;
    }

}
