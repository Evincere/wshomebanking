package com.bbva.wshomebanking.application.services;

import com.bbva.wshomebanking.application.repository.IAccountRepository;
import com.bbva.wshomebanking.application.repository.IClientAccountRepository;
import com.bbva.wshomebanking.application.repository.IClientRepository;
import com.bbva.wshomebanking.application.usecases.transaction.IDepositUseCase;
import com.bbva.wshomebanking.application.usecases.transaction.ITransactionCreateUseCase;
import com.bbva.wshomebanking.application.usecases.transaction.ITransactionSaveUseCase;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.domain.models.transaction.Deposit;
import com.bbva.wshomebanking.domain.models.transaction.Transaction;
import com.bbva.wshomebanking.presentation.request.transaction.DepositRequest;
import com.bbva.wshomebanking.utilities.TransactionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService implements IDepositUseCase {

    private static IClientRepository clientRepository;
    private static IAccountRepository accountRepository;
    private static IClientAccountRepository clientAccountRepository;
    @Override
    public TransactionResult deposit(DepositRequest request) {
        Optional<Client> client = clientRepository.findById(request.getClientId());
        Optional<Account> account = accountRepository.findById(request.getAccountId());
        //Optional<ClientAccount> clientAccount = clientAccountRepository.
    }
}
