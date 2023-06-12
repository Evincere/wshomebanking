package com.bbva.wshomebanking.application.usecases.transaction;

import com.bbva.wshomebanking.presentation.request.transaction.DepositRequest;
import com.bbva.wshomebanking.utilities.TransactionResult;

public interface IDepositUseCase {
    TransactionResult deposit(DepositRequest depositRequest);
}
