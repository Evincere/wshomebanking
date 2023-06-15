package com.bbva.wshomebanking.application.repository;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.transaction.Deposit;
import com.bbva.wshomebanking.domain.models.transaction.Transaction;
import com.bbva.wshomebanking.domain.models.Client;

import java.math.BigDecimal;

public interface ITransactionRepository {

    Transaction executeDeposit(Deposit deposit);

}
