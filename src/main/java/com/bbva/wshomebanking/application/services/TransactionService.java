package com.bbva.wshomebanking.application.services;

import com.bbva.wshomebanking.application.usecases.transaction.ITransactionCreateUseCase;
import com.bbva.wshomebanking.application.usecases.transaction.ITransactionSaveUseCase;
import com.bbva.wshomebanking.domain.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionCreateUseCase, ITransactionSaveUseCase {
    @Override
    public Transaction create(Transaction transaction) {
        return null;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return null;
    }
}
