package com.bbva.wshomebanking.application.usecases.transaction;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.transaction.Transaction;

import java.util.List;

public interface ITransactionListUseCase {
    List<Transaction> getTransactionList();
}
