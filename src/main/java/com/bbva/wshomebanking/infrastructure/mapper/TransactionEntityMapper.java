package com.bbva.wshomebanking.infrastructure.mapper;

import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.domain.models.Transaction;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import com.bbva.wshomebanking.infrastructure.entities.TransactionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component()
@RequiredArgsConstructor
public class TransactionEntityMapper {

    public TransactionEntity domainToEntity(Transaction transaction) {

        return TransactionEntity.builder()
                .id(transaction.getId())
                .transactionType(transaction.getTransactionType())
                .accountTo(transaction.getAccountToCBU())
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
