package com.bbva.wshomebanking.presentation.controllers;

import com.bbva.wshomebanking.application.usecases.client.*;
import com.bbva.wshomebanking.application.usecases.account.IAccountCreateUseCase;
import com.bbva.wshomebanking.application.usecases.account.IAccountSaveUseCase;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.presentation.mapper.ClientPresentationMapper;
import com.bbva.wshomebanking.presentation.request.client.ClientCreateRequest;
import com.bbva.wshomebanking.presentation.request.client.ClientFindRequest;
import com.bbva.wshomebanking.presentation.request.client.ClientUpdateRequest;
import com.bbva.wshomebanking.presentation.response.client.ClientCreateResponse;
import com.bbva.wshomebanking.presentation.response.client.ClientFindResponse;
import com.bbva.wshomebanking.presentation.response.errors.ErrorResponse;
import com.bbva.wshomebanking.utilities.AppConstants;
import com.bbva.wshomebanking.utilities.exceptions.ExistingPersonalIdException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final IClientCreateUseCase clientCreateUseCase;
    private final IClientUpdateUseCase clientUpdateUseCase;
    private final IClientFindByUseCase clientFindByUseCase;
    private final IClientListUseCase clientListUseCase;
    private final ClientPresentationMapper clientMapper;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody ClientCreateRequest request, BindingResult bindingResult) {

        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }

        try {
            ClientCreateResponse client = clientCreateUseCase.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(client);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), null));
        }
    }

    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> update(@Valid @RequestBody ClientUpdateRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        try {
            ClientCreateResponse client = clientUpdateUseCase.update(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(client);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), null));
        }
    }

    @PostMapping(value = "/find", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> find(@Valid @RequestBody ClientFindRequest request, BindingResult bindingResult) {

        try {
            Optional<Client> client = null;

            if(request.getId() != 0 )
                client = clientFindByUseCase.findById(request.getId());
            else if (!request.getPersonalId().equals(""))
                client = clientFindByUseCase.findByPersonalId(request.getPersonalId());

            if(client == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Cliente no encontrado", null));

            return ResponseEntity.status(HttpStatus.FOUND).body(clientMapper.findOneToResponse(client.get()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), null));
        }
    }

    @PostMapping(value = "/list", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> list(@Valid @RequestBody ClientFindRequest request, BindingResult bindingResult) {

        try {
            List<Client> clientList = null;
            List<ClientFindResponse> clientFindResponseList = new ArrayList<>();

            clientList = clientListUseCase.getClientsList(request);

            if(clientList == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("No se pudo obtener la lista de clientes", null));

            for (Client client :
                    clientList) {
                clientFindResponseList.add(clientMapper.findOneToResponse(client));
            }

            return ResponseEntity.status(HttpStatus.FOUND).body(clientFindResponseList);

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
