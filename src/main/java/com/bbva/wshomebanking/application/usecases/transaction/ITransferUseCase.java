package com.bbva.wshomebanking.application.usecases.transaction;

import com.bbva.wshomebanking.presentation.request.transaction.ExtractionRequest;
import com.bbva.wshomebanking.presentation.request.transaction.TransferRequest;
import com.bbva.wshomebanking.utilities.TransactionResponse;
import com.bbva.wshomebanking.utilities.exceptions.TransactionException;

public interface ITransferUseCase {
    TransactionResponse transfer(TransferRequest extractRequest) throws TransactionException;
}
