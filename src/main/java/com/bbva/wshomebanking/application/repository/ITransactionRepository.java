package com.bbva.wshomebanking.application.repository;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.transaction.Deposit;
import com.bbva.wshomebanking.domain.models.transaction.Extraction;
import com.bbva.wshomebanking.domain.models.transaction.Transaction;
import com.bbva.wshomebanking.domain.models.transaction.Transfer;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.utilities.exceptions.RecordNotFoundException;
import com.bbva.wshomebanking.utilities.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ITransactionRepository {

    Deposit executeDeposit(Deposit deposit) throws TransactionException;
    Extraction executeExtraction(Extraction extraction) throws TransactionException;
    Transfer executeTransfer(Transfer transfer) throws TransactionException;
    Optional<Transaction> findById(int id) throws RecordNotFoundException;
    List<Transaction> findAll();

}
