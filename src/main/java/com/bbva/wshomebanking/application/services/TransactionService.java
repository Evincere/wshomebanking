package com.bbva.wshomebanking.application.services;

import com.bbva.wshomebanking.application.repository.IAccountRepository;
import com.bbva.wshomebanking.application.repository.IClientAccountRepository;
import com.bbva.wshomebanking.application.repository.IClientRepository;
import com.bbva.wshomebanking.application.repository.ITransactionRepository;
import com.bbva.wshomebanking.application.usecases.transaction.IDepositUseCase;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.domain.models.transaction.Deposit;
import com.bbva.wshomebanking.presentation.request.transaction.DepositRequest;
import com.bbva.wshomebanking.utilities.TransactionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService implements IDepositUseCase {

    private final IClientRepository clientRepository;
    private final IAccountRepository accountRepository;
    private final IClientAccountRepository clientAccountRepository;
    private final ITransactionRepository transactionRepository;
    @Override
    public TransactionResult deposit(DepositRequest request) {

        ClientAccount clientAccount = clientAccountRepository.get(request.getClientId(),request.getAccountId());
        Deposit deposit = new Deposit(clientAccount, request.getAmount());
        if(deposit.isValid())
        {
            deposit.applyFundsMovements();
        }

        transactionRepository.executeDeposit(deposit);

        return null;
    }
}
