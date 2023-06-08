package com.bbva.wshomebanking.application.usecases.transaction;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.Transaction;

public interface ITransactionCreateUseCase {
    Transaction create(Transaction transaction);
}
