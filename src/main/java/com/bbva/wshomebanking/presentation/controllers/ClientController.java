package com.bbva.wshomebanking.presentation.controllers;

import com.bbva.wshomebanking.application.usecases.client.*;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.presentation.mapper.ClientPresentationMapper;
import com.bbva.wshomebanking.presentation.request.EnableDisableRequest;
import com.bbva.wshomebanking.presentation.request.client.ClientCreateRequest;
import com.bbva.wshomebanking.presentation.request.client.ClientFindRequest;
import com.bbva.wshomebanking.presentation.request.client.ClientUpdateRequest;
import com.bbva.wshomebanking.presentation.response.client.ClientCreateResponse;
import com.bbva.wshomebanking.presentation.response.client.ClientFindResponse;
import com.bbva.wshomebanking.presentation.response.errors.ErrorResponse;
import com.bbva.wshomebanking.utilities.*;
import com.bbva.wshomebanking.utilities.exceptions.CannotUpdatePersonalIdException;
import com.bbva.wshomebanking.utilities.exceptions.ErrorWhenSavingException;
import com.bbva.wshomebanking.utilities.exceptions.ExistingPersonalIdException;
import com.bbva.wshomebanking.utilities.exceptions.RecordNotFoundException;

import javax.validation.Valid;
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
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final IClientCreateUseCase clientCreateUseCase;
    private final IClientUpdateUseCase clientUpdateUseCase;
    private final IClientFindByUseCase clientFindByUseCase;
    private final IClientListUseCase clientListUseCase;
    private final IClientEnableDisableUseCase enableDisableUseCase;
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

        } catch (ExistingPersonalIdException e) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add(ErrorDescriptions.EXISTING_PERSONAL_ID);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), errors));
        } catch (ErrorWhenSavingException e) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add(ErrorDescriptions.ERROR_WHEN_SAVING_CLIENT);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), null));
        }
    }

    @PatchMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> update(@Valid @RequestBody ClientUpdateRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        try {
            Client requestedClient = clientFindByUseCase.findById(request.getId()).orElse(null);
            Client authenticatedClient = clientFindByUseCase.findByPersonalId(ControllerUtils.getUserName()).orElse(null);

            if(requestedClient == null)
                throw new Exception(ErrorDescriptions.CLIENT_NOT_FOUND);

            if(ControllerUtils.isNotAdmin() && requestedClient.getId() != authenticatedClient.getId())
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");

            if(ControllerUtils.isNotAdmin())
                if(!request.getPersonalId().equals(authenticatedClient.getPersonalId()))
                    throw new CannotUpdatePersonalIdException(ErrorCodes.CANNOT_UPDATE_PERSONAL_ID);

            ClientCreateResponse updatedClient = clientUpdateUseCase.update(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(updatedClient);

        } catch (ExistingPersonalIdException e) {
            return ControllerUtils.getBadRequest(e, ErrorDescriptions.EXISTING_PERSONAL_ID);
        } catch (ErrorWhenSavingException e) {
            return ControllerUtils.getBadRequest(e, ErrorDescriptions.ERROR_WHEN_SAVING_CLIENT);
        } catch (RecordNotFoundException e) {
            return ControllerUtils.getBadRequest(e, ErrorDescriptions.CLIENT_NOT_FOUND);
        } catch (CannotUpdatePersonalIdException e) {
            return ControllerUtils.getBadRequest(e, ErrorDescriptions.CANNOT_UPDATE_PERSONAL_ID);
        } catch (Exception e ){
            return ControllerUtils.getBadRequest(e, "");
        }
    }

    @GetMapping(value = "/myprofile")
    public ResponseEntity<?> myAccount() {

        try {
            Optional<Client> client = null;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            client = clientFindByUseCase.findByPersonalId(username);

            if(!client.isPresent()){
                ArrayList<String> errors = new ArrayList<>();
                errors.add(ErrorDescriptions.CLIENT_NOT_FOUND);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorCodes.RECORD_NOT_FOUND, errors));
            }
            return ResponseEntity.status(HttpStatus.FOUND).body(clientMapper.findOneToResponse(client.get()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), null));
        }
    }

    @GetMapping(value = "/find", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> find(@Valid @RequestBody ClientFindRequest request, BindingResult bindingResult) {

        try {
            Optional<Client> client = null;

            // Search by ID
            if(request.getId() != 0 )
                client = clientFindByUseCase.findById(request.getId());
            // Search by PersonalId
            else if (!request.getPersonalId().equals(""))
                client = clientFindByUseCase.findByPersonalId(request.getPersonalId());

            if(!client.isPresent()){
                ArrayList<String> errors = new ArrayList<>();
                errors.add(ErrorDescriptions.CLIENT_NOT_FOUND);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorCodes.RECORD_NOT_FOUND, errors));
            }


            return ResponseEntity.status(HttpStatus.FOUND).body(clientMapper.findOneToResponse(client.get()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), null));
        }
    }

    @GetMapping(value = "/list", produces = "application/json")
    public ResponseEntity<?> list() {

        try {
            List<Client> clientList = null;
            List<ClientFindResponse> clientFindResponseList = new ArrayList<>();

            clientList = clientListUseCase.getClientsList();

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

    @PatchMapping(value = "/enable", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> enable(@Valid @RequestBody EnableDisableRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        try {
            request.setActive(true);
            ClientCreateResponse updatedClient = enableDisableUseCase.switchActive(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(updatedClient);

        } catch (ErrorWhenSavingException e) {
            return ControllerUtils.getBadRequest(e, ErrorDescriptions.ERROR_WHEN_SAVING_CLIENT);
        } catch (RecordNotFoundException e) {
            return ControllerUtils.getBadRequest(e, ErrorDescriptions.CLIENT_NOT_FOUND);
        } catch (Exception e ){
            return ControllerUtils.getBadRequest(e, "");
        }
    }

    @PatchMapping(value = "/disable", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> disable(@Valid @RequestBody EnableDisableRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        try {
            request.setActive(false);
            ClientCreateResponse updatedClient = enableDisableUseCase.switchActive(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(updatedClient);

        } catch (ErrorWhenSavingException e) {
            return ControllerUtils.getBadRequest(e, ErrorDescriptions.ERROR_WHEN_SAVING_CLIENT);
        } catch (RecordNotFoundException e) {
            return ControllerUtils.getBadRequest(e, ErrorDescriptions.CLIENT_NOT_FOUND);
        } catch (Exception e ){
            return ControllerUtils.getBadRequest(e, "");
        }
    }

    private static ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            ErrorResponse errorResponse = new ErrorResponse(ErrorCodes.VALIDATION_ERROR, errors);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        return null;
    }



}
