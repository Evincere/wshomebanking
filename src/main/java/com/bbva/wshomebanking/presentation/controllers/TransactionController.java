package com.bbva.wshomebanking.presentation.controllers;

import com.bbva.wshomebanking.application.repository.ITransactionRepository;
import com.bbva.wshomebanking.application.usecases.account.IAccountFindByUseCase;
import com.bbva.wshomebanking.application.usecases.account.IMyAccountsUseCase;
import com.bbva.wshomebanking.application.usecases.client.IClientFindByUseCase;
import com.bbva.wshomebanking.application.usecases.transaction.*;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.transaction.Transaction;
import com.bbva.wshomebanking.presentation.mapper.ClientAccountPresentationMapper;
import com.bbva.wshomebanking.presentation.mapper.TransactionPresentationMapper;
import com.bbva.wshomebanking.presentation.request.transaction.DepositRequest;
import com.bbva.wshomebanking.presentation.request.transaction.ExtractionRequest;
import com.bbva.wshomebanking.presentation.request.transaction.TransferRequest;
import com.bbva.wshomebanking.presentation.response.account.AccountResponse;
import com.bbva.wshomebanking.presentation.response.account.MyAccountsResponse;
import com.bbva.wshomebanking.presentation.response.errors.ErrorResponse;
import com.bbva.wshomebanking.utilities.ErrorCodes;
import com.bbva.wshomebanking.utilities.ErrorDescriptions;
import com.bbva.wshomebanking.utilities.TransactionResponse;
import javax.validation.Valid;

import com.bbva.wshomebanking.utilities.exceptions.ClientNotFoundException;
import com.bbva.wshomebanking.utilities.exceptions.TransactionNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final IDepositUseCase depositUseCase;
    private final IExtractUseCase extractUseCase;
    private final ITransferUseCase transferUseCase;
    private final IMyAccountsUseCase myAccountsUseCase;
    private final IAccountFindByUseCase accountFindByUseCase;
    private final IClientFindByUseCase clientFindByUseCase;
    private final ITransactionListUseCase transactionListUseCase;
    private final ITransactionFindByUseCase transactionFindByUseCase;
    private final TransactionPresentationMapper transactionPresentationMapper;


    @PostMapping(value = "/deposit", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> deposit(@Valid @RequestBody DepositRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        try {
            if(!isTransactionAllowed(request.getAccountId(), request.getClientId()))
                throw new TransactionNotAllowedException(ErrorCodes.TRANSACTION_NOT_ALLOWED);
            TransactionResponse result = depositUseCase.deposit(request);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
        } catch (TransactionNotAllowedException e){
            ArrayList<String> errors = new ArrayList<>();
            errors.add(ErrorDescriptions.TRANSACTION_NOT_ALLOWED);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), errors));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(value = "/extraction", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> extraction(@Valid @RequestBody ExtractionRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        try {
            if(!isTransactionAllowed(request.getAccountId(), request.getClientId()))
                throw new TransactionNotAllowedException(ErrorCodes.TRANSACTION_NOT_ALLOWED);
            TransactionResponse result = extractUseCase.extract(request);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
        } catch (TransactionNotAllowedException e){
            ArrayList<String> errors = new ArrayList<>();
            errors.add(ErrorDescriptions.TRANSACTION_NOT_ALLOWED);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), errors));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(value = "/transfer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> transfer(@Valid @RequestBody TransferRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        try {
            if(!isTransactionAllowed(request.getAccountId(), request.getClientId()))
                throw new TransactionNotAllowedException(ErrorCodes.TRANSACTION_NOT_ALLOWED);
            TransactionResponse result = transferUseCase.transfer(request);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
        } catch (TransactionNotAllowedException e){
            ArrayList<String> errors = new ArrayList<>();
            errors.add(ErrorDescriptions.TRANSACTION_NOT_ALLOWED);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), errors));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private static ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            ErrorResponse errorResponse = new ErrorResponse("Error de validación", errors);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        return null;
    }

    private boolean isTransactionAllowed(int accountId, int clientId) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            Client client = clientFindByUseCase.findById(clientId).orElse(null);

            if(!client.getPersonalId().equals(username))
                return false;

            List<MyAccountsResponse> myAccountsResponseList = myAccountsUseCase.getMyAccounts(username);

            for (MyAccountsResponse account : myAccountsResponseList) {
                if(account.getId() == accountId)
                    return areClientAndAccountActive(accountId,clientId);
            }

        } catch (ClientNotFoundException e) {
            return false;
        }

        return false;
    }

    private boolean areClientAndAccountActive(int accountId, int clientId) {
        Client client = clientFindByUseCase.findById(clientId).orElse(null);
        if(client!=null)
            if(!client.isActive())
                return false;

        Account account = accountFindByUseCase.findById(accountId).orElse(null);
        if(account!=null)
            if(!account.isActive())
                return false;

        return true;
    }

    @GetMapping(value = "/list", produces = "application/json")
    public ResponseEntity<?> list() {

        try {
            List<Transaction> transactionList = null;
            List<TransactionResponse>transactionResponses = new ArrayList<>();

            transactionFindByUseCase.findByClientId(1);

            transactionList = transactionListUseCase.getTransactionList();

            if(transactionList == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("No se pudo obtener la lista de cuentas", null));

            for (Transaction transaction :
                    transactionList) {
                transactionResponses.add(transactionPresentationMapper.domainToResponse(transaction));
            }

            return ResponseEntity.status(HttpStatus.FOUND).body(transactionResponses);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), null));
        }
    }

    @GetMapping(value = "/client/{id}", produces = "application/json")
    public ResponseEntity<?> clientTransactions(@PathVariable("id") int clientId) {

        try {
            List<Transaction> transactionList = null;
            List<TransactionResponse>transactionResponses = new ArrayList<>();

            transactionList = transactionFindByUseCase.findByClientId(clientId);

            if(transactionList == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("No se pudo obtener la lista de cuentas", null));

            for (Transaction transaction :
                    transactionList) {
                transactionResponses.add(transactionPresentationMapper.domainToResponse(transaction));
            }

            return ResponseEntity.status(HttpStatus.FOUND).body(transactionResponses);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), null));
        }
    }

    @GetMapping(value = "/account/{id}", produces = "application/json")
    public ResponseEntity<?> accountTransactions(@PathVariable("id") int accountId) {

        try {
            List<Transaction> transactionList = null;
            List<TransactionResponse>transactionResponses = new ArrayList<>();

            transactionList = transactionFindByUseCase.findByAccountId(accountId);

            if(transactionList == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("No se pudo obtener la lista de cuentas", null));

            for (Transaction transaction :
                    transactionList) {
                transactionResponses.add(transactionPresentationMapper.domainToResponse(transaction));
            }

            return ResponseEntity.status(HttpStatus.FOUND).body(transactionResponses);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), null));
        }
    }

    @GetMapping(value = "/mytransactions", produces = "application/json")
    public ResponseEntity<?> myTransactions() {
        try {
            List<Transaction> transactionList = null;
            List<TransactionResponse>transactionResponses = new ArrayList<>();
            Optional<Client> client = null;

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            client = clientFindByUseCase.findByPersonalId(username);

            transactionList = transactionFindByUseCase.findByClientId(client.get().getId());

            if(transactionList == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("No se pudo obtener la lista de cuentas", null));

            for (Transaction transaction :
                    transactionList) {
                transactionResponses.add(transactionPresentationMapper.domainToResponse(transaction));
            }

            return ResponseEntity.status(HttpStatus.FOUND).body(transactionResponses);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), null));
        }
    }

}
