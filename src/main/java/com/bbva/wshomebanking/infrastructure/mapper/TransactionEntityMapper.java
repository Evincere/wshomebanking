package com.bbva.wshomebanking.infrastructure.mapper;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.domain.models.transaction.Deposit;
import com.bbva.wshomebanking.domain.models.transaction.Extraction;
import com.bbva.wshomebanking.domain.models.transaction.Transaction;
import com.bbva.wshomebanking.infrastructure.entities.TransactionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component()
@RequiredArgsConstructor
public class TransactionEntityMapper {

    public TransactionEntity domainToEntity(Transaction transaction) {

        return TransactionEntity.builder()
                .id(transaction.getId())
                .transactionType(transaction.getTransactionType())
                //.accountTo(transaction.getAccountTo())
                .build();
    }

    public Deposit depositEntityToDomain(TransactionEntity transactionEntity) {
        Deposit deposit = new Deposit();

        Client client = new Client();
        client.setId(transactionEntity.getClientId());

        Account account = new Account();
        account.setId(transactionEntity.getAccountId());

        ClientAccount clientAccount = new ClientAccount();
        clientAccount.setClient(client);
        clientAccount.setAccount(account);
        clientAccount.setAccountHolderType(transactionEntity.getClientAccount().getHolderType());

        deposit.setId(transactionEntity.getId());
        deposit.setAmount(transactionEntity.getAmount());
        deposit.setTransactionType(transactionEntity.getTransactionType());
        deposit.setAccount(clientAccount);

        return deposit;
    }

    public Extraction extractionEntityToDomain(TransactionEntity transactionEntity) {
        Extraction extraction = new Extraction();

        Client client = new Client();
        client.setId(transactionEntity.getClientId());

        Account account = new Account();
        account.setId(transactionEntity.getAccountId());

        ClientAccount clientAccount = new ClientAccount();
        clientAccount.setClient(client);
        clientAccount.setAccount(account);
        clientAccount.setAccountHolderType(transactionEntity.getClientAccount().getHolderType());

        extraction.setId(transactionEntity.getId());
        extraction.setAmount(transactionEntity.getAmount());
        extraction.setTransactionType(transactionEntity.getTransactionType());
        extraction.setAccount(clientAccount);

        return extraction;
    }

}
