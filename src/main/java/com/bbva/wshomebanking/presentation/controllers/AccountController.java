package com.bbva.wshomebanking.presentation.controllers;

import com.bbva.wshomebanking.application.usecases.client.IClientFindByUseCase;
import com.bbva.wshomebanking.application.usecases.account.IAccountCreateUseCase;
import com.bbva.wshomebanking.application.usecases.account.IAccountSaveUseCase;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.presentation.mapper.AccountPresentationMapper;
import com.bbva.wshomebanking.presentation.request.cuenta.AccountCreateRequest;
import com.bbva.wshomebanking.presentation.response.account.AccountResponse;
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

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final IAccountCreateUseCase accountCreateUseCase;
    private final IClientFindByUseCase clientFindByUseCase;
    private final IAccountSaveUseCase accountSaveUseCase;
    private final AccountPresentationMapper accountPresentationMapper;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody AccountCreateRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }

        Optional<Client> client = clientFindByUseCase.findById(request.getClientId());


        Account account = accountCreateUseCase.create(client.get(), request.getCurrency());
        Account savedAccount = accountSaveUseCase.save(account);

        /*
        Client savedClient = clienteSaveUseCase.save(client, account);

        ClientResponse response = clientMapper.domainToResponse(savedClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        */
        AccountResponse response = accountPresentationMapper.domainToResponse(savedAccount);
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
