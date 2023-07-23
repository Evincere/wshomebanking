package com.bbva.wshomebanking.infrastructure.repositories;

import com.bbva.wshomebanking.application.repository.ITransactionRepository;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.transaction.Deposit;
import com.bbva.wshomebanking.domain.models.transaction.Extraction;
import com.bbva.wshomebanking.domain.models.transaction.Transaction;
import com.bbva.wshomebanking.domain.models.transaction.Transfer;
import com.bbva.wshomebanking.infrastructure.entities.AccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountId;
import com.bbva.wshomebanking.infrastructure.entities.TransactionEntity;
import com.bbva.wshomebanking.infrastructure.mapper.AccountEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.ClientEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.TransactionEntityMapper;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IClientAccountSpringRepository;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.ITransactionSpringRepository;
import com.bbva.wshomebanking.utilities.ErrorCodes;
import com.bbva.wshomebanking.utilities.TransactionTypes;
import com.bbva.wshomebanking.utilities.exceptions.RecordNotFoundException;
import com.bbva.wshomebanking.utilities.exceptions.TransactionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements ITransactionRepository {
    private final TransactionEntityMapper transactionEntityMapper;
    private final IClientAccountSpringRepository clientAccountSpringRepository;
    private final ITransactionSpringRepository transactionSpringRepository;

    @Override
    public Deposit executeDeposit(Deposit deposit) throws TransactionException {
        try {
            Optional<ClientAccountEntity> clientAccountEntity = clientAccountSpringRepository.findById(
                    new ClientAccountId(
                            deposit.getAccount().getAccount().getId(),
                            deposit.getAccount().getClient().getId()
                    ));
            clientAccountEntity.get().getAccount().setBalance(deposit.getAccount().getAccount().getBalance());

            TransactionEntity transactionEntity = TransactionEntity.builder()
                    .clientAccount(clientAccountEntity.get())
                    .clientId(clientAccountEntity.get().getClient().getId())
                    .accountId(clientAccountEntity.get().getAccount().getId())
                    .transactionType(deposit.getTransactionType())
                    .amount(deposit.getAmount())
                    .build();

            transactionEntity = transactionSpringRepository.save(transactionEntity);
            return transactionEntityMapper.depositEntityToDomain(transactionEntity);
        } catch (Exception e) {
            throw new TransactionException("No se pudo realizar el deposito");
        }
    }

    @Override
    public Extraction executeExtraction(Extraction extraction) throws TransactionException {
        try {
            Optional<ClientAccountEntity> clientAccountEntity = clientAccountSpringRepository.findById(
                    new ClientAccountId(
                            extraction.getAccount().getAccount().getId(),
                            extraction.getAccount().getClient().getId()
                    ));
            clientAccountEntity.get().getAccount().setBalance(extraction.getAccount().getAccount().getBalance());

            TransactionEntity transactionEntity = TransactionEntity.builder()
                    .clientAccount(clientAccountEntity.get())
                    .clientId(clientAccountEntity.get().getClient().getId())
                    .accountId(clientAccountEntity.get().getAccount().getId())
                    .transactionType(extraction.getTransactionType())
                    .amount(extraction.getAmount())
                    .build();

            transactionEntity = transactionSpringRepository.save(transactionEntity);
            return transactionEntityMapper.extractionEntityToDomain(transactionEntity);
        } catch (Exception e) {
            throw new TransactionException("No se pudo realizar el deposito");
        }
    }

    @Override
    public Transfer executeTransfer(Transfer transfer) throws TransactionException {
        try {
            Optional<ClientAccountEntity> clientAccountEntity = clientAccountSpringRepository.findById(
                    new ClientAccountId(
                            transfer.getAccount().getAccount().getId(),
                            transfer.getAccount().getClient().getId()
                    ));
            clientAccountEntity.get().getAccount().setBalance(transfer.getAccount().getAccount().getBalance());

            TransactionEntity transactionEntity = TransactionEntity.builder()
                    .clientAccount(clientAccountEntity.get())
                    .clientId(clientAccountEntity.get().getClient().getId())
                    .accountId(clientAccountEntity.get().getAccount().getId())
                    .transactionType(transfer.getTransactionType())
                    .amount(transfer.getAmount())
                    .build();

            transactionEntity = transactionSpringRepository.save(transactionEntity);
            return transactionEntityMapper.transferEntityToDomain(transactionEntity);
        } catch (Exception e) {
            throw new TransactionException("No se pudo realizar la transferencia");
        }
    }

    @Override
    public Optional<Transaction> findById(int id) throws RecordNotFoundException {

        Optional<TransactionEntity> entity = transactionSpringRepository.findById(id);

        if(entity.isEmpty()) throw new RecordNotFoundException(ErrorCodes.RECORD_NOT_FOUND);

        if(entity.get().getTransactionType().equals(TransactionTypes.DEPOSIT))
            return Optional.ofNullable(transactionEntityMapper.depositEntityToDomain(entity.get()));
        if(entity.get().getTransactionType().equals(TransactionTypes.TRANSFER))
            return Optional.ofNullable(transactionEntityMapper.transferEntityToDomain(entity.get()));
        if(entity.get().getTransactionType().equals(TransactionTypes.WHITDRAWAL))
            return Optional.ofNullable(transactionEntityMapper.extractionEntityToDomain(entity.get()));

        throw new RecordNotFoundException(ErrorCodes.RECORD_NOT_FOUND);
    }

    @Override
    public List<Transaction> findAll() {
        List<TransactionEntity> transactionEntityList = new ArrayList<>();
        List<Transaction> transactionList = new ArrayList<>();

        transactionEntityList = transactionSpringRepository.findAll();

        for (TransactionEntity entity : transactionEntityList) {
            if(entity.getTransactionType().equals(TransactionTypes.DEPOSIT))
                transactionList.add(transactionEntityMapper.depositEntityToDomain(entity));
            if(entity.getTransactionType().equals(TransactionTypes.TRANSFER))
                transactionList.add(transactionEntityMapper.transferEntityToDomain(entity));
            if(entity.getTransactionType().equals(TransactionTypes.WHITDRAWAL))
                transactionList.add(transactionEntityMapper.extractionEntityToDomain(entity));
        }

        return transactionList;
    }
}
