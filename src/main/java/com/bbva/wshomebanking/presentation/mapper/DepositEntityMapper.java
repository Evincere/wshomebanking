package com.bbva.wshomebanking.presentation.mapper;

import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.transaction.Deposit;
import com.bbva.wshomebanking.domain.models.transaction.Transaction;
import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import com.bbva.wshomebanking.infrastructure.entities.TransactionEntity;

import java.util.ArrayList;

public class DepositEntityMapper {
    public TransactionEntity domainToEntity(Deposit transaction) {

        return TransactionEntity.builder()
                .id(transaction.getId())
                .transactionType(transaction.getTransactionType())
                //.accountTo(transaction.getAccountTo())
                .build();
    }

    public Client entityToDomain(ClientEntity clientEntity) {
        Client cliente = new Client();
        cliente.setId(clientEntity.getId());
        cliente.setPersonalId(clientEntity.getPersonalId());
        cliente.setFirstName(clientEntity.getFirstName());
        cliente.setLastName(clientEntity.getLastName());
        cliente.setEmail(clientEntity.getEmail());
        cliente.setPhone(clientEntity.getPhone());
        cliente.setAddress(clientEntity.getAddress());
        cliente.setAccounts(new ArrayList<>());

        return cliente;
    }
}
