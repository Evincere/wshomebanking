package com.bbva.wshomebanking.presentation.controllers;

import com.bbva.wshomebanking.application.usecases.account.IAccountFindByUseCase;
import com.bbva.wshomebanking.application.usecases.client.IClientFindByUseCase;
import com.bbva.wshomebanking.application.usecases.account.IAccountCreateUseCase;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.presentation.mapper.AccountPresentationMapper;
import com.bbva.wshomebanking.presentation.request.account.AccountCreateRequest;
import com.bbva.wshomebanking.presentation.request.account.AccountFindRequest;
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
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final IClientFindByUseCase clientFindByUseCase;
    private final IAccountCreateUseCase accountCreateUseCase;
    private final IAccountFindByUseCase accountFindByUseCase;
    private final AccountPresentationMapper accountPresentationMapper;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody AccountCreateRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        Account savedAccount = accountCreateUseCase.create(request.getClientId(), request.getCurrency());
        return ResponseEntity.status(HttpStatus.CREATED).body(accountPresentationMapper.domainToResponse(savedAccount));
    }

    @PostMapping(value = "/find", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> find(@Valid @RequestBody AccountFindRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        try {
            Optional<Account> account = null;

            if(request.getAccountId() != 0 )
                account = accountFindByUseCase.findById(request.getAccountId());

            if(account == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Cliente no encontrado", null));

            return ResponseEntity.status(HttpStatus.FOUND).body(accountPresentationMapper.findOneToResponse(account.get()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), null));
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

}
