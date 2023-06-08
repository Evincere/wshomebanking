package com.bbva.wshomebanking.application.repository;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Transaction;
import com.bbva.wshomebanking.domain.models.Client;

import java.math.BigDecimal;

public interface ITransactionRepository {

    Transaction saveTransaction(Client client, Account account, String transactionType, BigDecimal amount);

}
