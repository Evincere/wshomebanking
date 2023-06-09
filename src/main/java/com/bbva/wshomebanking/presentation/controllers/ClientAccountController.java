package com.bbva.wshomebanking.presentation.controllers;

import com.bbva.wshomebanking.application.repository.IClientAccountRepository;
import com.bbva.wshomebanking.application.usecases.account.IAccountFindByUseCase;
import com.bbva.wshomebanking.application.usecases.client.IClientFindByUseCase;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.presentation.mapper.ClientAccountPresentationMapper;
import com.bbva.wshomebanking.presentation.request.clientaccount.ClientAccountCreateRequest;
import com.bbva.wshomebanking.presentation.response.clientaccount.ClientAccountResponse;
import com.bbva.wshomebanking.presentation.response.errors.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientaccount")
@RequiredArgsConstructor
public class ClientAccountController {

    private final IClientFindByUseCase clientFindByUseCase;
    private final IAccountFindByUseCase accountFindByUseCase;
    private final IClientAccountRepository clientAccountRepository;
    private final ClientAccountPresentationMapper clientAccountPresentationMapper;

    @PostMapping(value = "/relate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody ClientAccountCreateRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }

        Optional<Client> client = clientFindByUseCase.findById(request.getClientId());
        Optional<Account> account = accountFindByUseCase.findById(request.getAccountId());

        ClientAccount savedClientAccount = clientAccountRepository.saveClientAccount(client.get(), account.get(), request.getHolderType());

        ClientAccountResponse response = clientAccountPresentationMapper.domainToResponse(savedClientAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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

}
