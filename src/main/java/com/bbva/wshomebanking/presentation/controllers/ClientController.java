package com.bbva.wshomebanking.presentation.controllers;

import com.bbva.wshomebanking.application.usecases.client.IClientCreateUseCase;
import com.bbva.wshomebanking.application.usecases.client.IClientSaveUseCase;
import com.bbva.wshomebanking.application.usecases.cuenta.IAccountCreateUseCase;
import com.bbva.wshomebanking.application.usecases.cuenta.IAccountSaveUseCase;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.enums.Currency;
import com.bbva.wshomebanking.presentation.mapper.ClientPresentationMapper;
import com.bbva.wshomebanking.presentation.request.client.ClientCreateRequest;
import com.bbva.wshomebanking.presentation.response.client.ClientResponse;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final IClientCreateUseCase clientCreateUseCase;
    private final IClientSaveUseCase clienteSaveUseCase;
    private final IAccountCreateUseCase accountCreateUseCase;
    private final IAccountSaveUseCase accountSaveUseCase;
    private final ClientPresentationMapper clientMapper;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody ClientCreateRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }

        Currency currency = Currency.ARS;

        Client client = clientCreateUseCase.create(request);
        Account account = accountCreateUseCase.create(client, currency);

        Client savedClient = clienteSaveUseCase.save(client);
        Account savedAccount = accountSaveUseCase.save(account);

        // Cuenta cuenta = cuentaCreateUseCase.create(??);
        ClientResponse response = clientMapper.domainToResponse(savedClient);
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
