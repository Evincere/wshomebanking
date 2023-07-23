package com.bbva.wshomebanking.application.services;

import com.bbva.wshomebanking.application.repository.IAccountRepository;
import com.bbva.wshomebanking.application.repository.IClientAccountRepository;
import com.bbva.wshomebanking.application.repository.IClientRepository;
import com.bbva.wshomebanking.application.repository.ITransactionRepository;
import com.bbva.wshomebanking.application.usecases.transaction.*;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.domain.models.transaction.Deposit;
import com.bbva.wshomebanking.domain.models.transaction.Extraction;
import com.bbva.wshomebanking.domain.models.transaction.Transaction;
import com.bbva.wshomebanking.domain.models.transaction.Transfer;
import com.bbva.wshomebanking.infrastructure.entities.TransactionEntity;
import com.bbva.wshomebanking.presentation.mapper.TransactionPresentationMapper;
import com.bbva.wshomebanking.presentation.request.transaction.DepositRequest;
import com.bbva.wshomebanking.presentation.request.transaction.ExtractionRequest;
import com.bbva.wshomebanking.presentation.request.transaction.TransferRequest;
import com.bbva.wshomebanking.utilities.TransactionResponse;
import com.bbva.wshomebanking.utilities.exceptions.TransactionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService implements IDepositUseCase, IExtractUseCase, ITransferUseCase, ITransactionListUseCase, ITransactionFindByUseCase {

    private final IClientRepository clientRepository;
    private final IAccountRepository accountRepository;
    private final IClientAccountRepository clientAccountRepository;
    private final ITransactionRepository transactionRepository;
    private final TransactionPresentationMapper transactionMapper;
    @Override
    public TransactionResponse deposit(DepositRequest request) throws TransactionException {

        ClientAccount clientAccount = clientAccountRepository.get(request.getClientId(),request.getAccountId());
        Deposit deposit = new Deposit(clientAccount, request.getAmount());
        if(deposit.isValid())
        {
            deposit.applyFundsMovements();
        }

        deposit = transactionRepository.executeDeposit(deposit);

        return transactionMapper.domainToResponse(deposit);
    }

    @Override
    public TransactionResponse extract(ExtractionRequest request) throws TransactionException {
        ClientAccount clientAccount = clientAccountRepository.get(request.getClientId(),request.getAccountId());
        Extraction extraction = new Extraction(clientAccount, request.getAmount());
        if(extraction.isValid())
        {
            extraction.applyFundsMovements();
        }
        else {
            throw new TransactionException("Fondos insuficientes");
        }

        extraction = transactionRepository.executeExtraction(extraction);

        return transactionMapper.domainToResponse(extraction);
    }

    @Override
    public TransactionResponse transfer(TransferRequest request) throws TransactionException {
        ClientAccount clientAccount = clientAccountRepository.get(request.getClientId(),request.getAccountId());
        Transfer transfer = new Transfer(clientAccount, request.getAmount());
        if(transfer.isValid())
        {
            transfer.applyFundsMovements();
        }
        else {
            throw new TransactionException("Fondos insuficientes");
        }

        transfer = transactionRepository.executeTransfer(transfer);

        return transactionMapper.domainToResponse(transfer);
    }

    @Override
    public List<Transaction> getTransactionList() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findByClientPersonalId(int personalId) {
        return null;
    }

    @Override
    public List<Transaction> findByClientId(int id) {
        return transactionRepository.findByClientId(id);
    }

    @Override
    public List<Transaction> findByAccountId(int accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}
