package com.bbva.wshomebanking.application.usecases.transaction;

import com.bbva.wshomebanking.domain.models.transaction.Transaction;

public interface ITransactionSaveUseCase {
    Transaction save(Transaction transaction);
}
