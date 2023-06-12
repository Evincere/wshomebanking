package com.bbva.wshomebanking.infrastructure.repositories;

import com.bbva.wshomebanking.application.repository.ITransactionRepository;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.transaction.Transaction;
import com.bbva.wshomebanking.infrastructure.entities.TransactionEntity;
import com.bbva.wshomebanking.infrastructure.mapper.AccountEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.ClientEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.TransactionEntityMapper;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.ITransactionSpringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements ITransactionRepository {
    private final TransactionEntityMapper transactionEntityMapper;
    private final ClientEntityMapper clientEntityMapper;
    private final AccountEntityMapper accountEntityMapper;
    private final ITransactionSpringRepository transactionRepository;


    @Override
    public Transaction saveTransaction(Client client, Account account, String transactionType, BigDecimal amount) {

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionType(transactionType);
        transactionEntity.setAccountId(account.getId());
        transactionEntity.setClientId(client.getId());
        transactionEntity.setAmount(amount);

        TransactionEntity savedTransaction = transactionRepository.save(transactionEntity);


        return null;
    }
}
