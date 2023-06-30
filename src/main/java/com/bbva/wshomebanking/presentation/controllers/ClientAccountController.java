package com.bbva.wshomebanking.presentation.controllers;

import com.bbva.wshomebanking.application.repository.IClientAccountRepository;
import com.bbva.wshomebanking.application.usecases.account.IAccountFindByUseCase;
import com.bbva.wshomebanking.application.usecases.client.IClientFindByUseCase;
import com.bbva.wshomebanking.application.usecases.clientaccount.IRelateClientAccountUseCase;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.presentation.mapper.ClientAccountPresentationMapper;
import com.bbva.wshomebanking.presentation.request.clientaccount.ClientAccountCreateRequest;
import com.bbva.wshomebanking.presentation.response.clientaccount.ClientAccountResponse;
import com.bbva.wshomebanking.presentation.response.errors.ErrorResponse;
import com.bbva.wshomebanking.utilities.ErrorCodes;
import com.bbva.wshomebanking.utilities.ErrorDescriptions;
import com.bbva.wshomebanking.utilities.exceptions.AccountNotFoundException;
import com.bbva.wshomebanking.utilities.exceptions.ClientNotFoundException;
import com.bbva.wshomebanking.utilities.exceptions.RelationshipNotCreatedException;
import javax.validation.Valid;
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
@RequestMapping("/clientaccount")
@RequiredArgsConstructor
public class ClientAccountController {

    private final IRelateClientAccountUseCase relateClientToAccount;
    private final ClientAccountPresentationMapper clientAccountPresentationMapper;

    @PostMapping(value = "/relate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody ClientAccountCreateRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        try {
            ClientAccount clientAccount = relateClientToAccount.relate(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientAccount);

        } catch (ClientNotFoundException e) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add(ErrorDescriptions.CLIENT_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorCodes.RECORD_NOT_FOUND, errors));
        } catch (AccountNotFoundException e) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add(ErrorDescriptions.ACCOUNT_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorCodes.RECORD_NOT_FOUND, errors));
        } catch (RelationshipNotCreatedException e) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add(ErrorDescriptions.RELATIONSHIP_ACCOUNT_CLIENT_NOT_CREATED);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorCodes.COULD_NOT_RELATE_CLIENT_ACCOUNT, errors));
        } catch (Exception e) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add(ErrorDescriptions.CLIENT_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorCodes.RECORD_NOT_FOUND, errors));
        }

        //TODO: Intentar crear relacion ya existente

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
