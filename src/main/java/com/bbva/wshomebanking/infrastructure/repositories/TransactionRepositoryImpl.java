package com.bbva.wshomebanking.infrastructure.repositories;

import com.bbva.wshomebanking.application.repository.ITransactionRepository;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.transaction.Deposit;
import com.bbva.wshomebanking.domain.models.transaction.Transaction;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountId;
import com.bbva.wshomebanking.infrastructure.entities.TransactionEntity;
import com.bbva.wshomebanking.infrastructure.mapper.AccountEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.ClientEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.TransactionEntityMapper;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IClientAccountSpringRepository;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.ITransactionSpringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements ITransactionRepository {
    private final TransactionEntityMapper transactionEntityMapper;
    private final ClientEntityMapper clientEntityMapper;
    private final AccountEntityMapper accountEntityMapper;
    private final ITransactionSpringRepository transactionRepository;
    private final IClientAccountSpringRepository clientAccountSpringRepository;
    private final ITransactionSpringRepository transactionSpringRepository;

    @Override
    public Transaction executeDeposit(Deposit deposit) {
        try {
            Optional<ClientAccountEntity> clientAccountEntity = clientAccountSpringRepository.findById(
                    new ClientAccountId(
                            deposit.getAccount().getClient().getId(),
                            deposit.getAccount().getAccount().getId()
                    ));
            clientAccountEntity.get().getAccount().setBalance(deposit.getAccount().getAccount().getBalance());

            TransactionEntity transactionEntity = TransactionEntity.builder()
                    .clientAccount(clientAccountEntity.get())
                    .clientId(clientAccountEntity.get().getClient().getId())
                    .accountId(clientAccountEntity.get().getAccount().getId())
                    .transactionType(deposit.getTransactionType())
                    .amount(deposit.getAmount())
                    .build();

            transactionSpringRepository.save(transactionEntity);
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
