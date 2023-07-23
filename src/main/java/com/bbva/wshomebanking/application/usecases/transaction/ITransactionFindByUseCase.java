package com.bbva.wshomebanking.application.usecases.transaction;

import com.bbva.wshomebanking.domain.models.transaction.Transaction;

import java.util.List;

public interface ITransactionFindByUseCase {
    List<Transaction> findByClientPersonalId(int personalId);
    List<Transaction> findByClientId(int clientId);
    List<Transaction> findByAccountId(int accountId);
}
