package com.bbva.wshomebanking.presentation.controllers;

import com.bbva.wshomebanking.application.usecases.cliente.IClienteCreateUseCase;
import com.bbva.wshomebanking.application.usecases.cliente.IClienteSaveUseCase;
import com.bbva.wshomebanking.domain.models.Cliente;
import com.bbva.wshomebanking.presentation.mapper.ClientePresentationMapper;
import com.bbva.wshomebanking.presentation.request.client.ClienteCreateRequest;
import com.bbva.wshomebanking.presentation.response.client.ClienteResponse;
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
public class ClienteController {

    private final IClienteCreateUseCase clientCreateUseCase;
    private final IClienteSaveUseCase clienteSaveUseCase;
    private final ClientePresentationMapper clientMapper;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody ClienteCreateRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }

        Cliente client = clientCreateUseCase.create(request);
        Cliente savedClient = clienteSaveUseCase.save(client);
        ClienteResponse response = clientMapper.domainToResponse(savedClient);
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
