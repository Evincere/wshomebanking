package com.bbva.wshomebanking.presentation.controllers;

import com.bbva.wshomebanking.application.usecases.account.IAccountFindByUseCase;
import com.bbva.wshomebanking.application.usecases.account.IAccountListUseCase;
import com.bbva.wshomebanking.application.usecases.account.IMyAccountsUseCase;
import com.bbva.wshomebanking.application.usecases.client.IClientFindByUseCase;
import com.bbva.wshomebanking.application.usecases.account.IAccountCreateUseCase;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.presentation.mapper.AccountPresentationMapper;
import com.bbva.wshomebanking.presentation.request.account.AccountCreateRequest;
import com.bbva.wshomebanking.presentation.request.account.AccountFindRequest;
import com.bbva.wshomebanking.presentation.response.account.AccountResponse;
import com.bbva.wshomebanking.presentation.response.account.MyAccountsResponse;
import com.bbva.wshomebanking.presentation.response.client.ClientFindResponse;
import com.bbva.wshomebanking.presentation.response.errors.ErrorResponse;
import com.bbva.wshomebanking.utilities.ErrorCodes;
import com.bbva.wshomebanking.utilities.ErrorDescriptions;
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
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final IAccountCreateUseCase accountCreateUseCase;
    private final IAccountFindByUseCase accountFindByUseCase;
    private final IAccountListUseCase accountListUseCase;
    private final IMyAccountsUseCase myAccountsUseCase;
    private final AccountPresentationMapper accountPresentationMapper;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody AccountCreateRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        try {
            Account savedAccount = accountCreateUseCase.create(request.getClientId(), request.getCurrency());
            return ResponseEntity.status(HttpStatus.CREATED).body(accountPresentationMapper.domainToResponse(savedAccount));
        } catch (RecordNotFoundException e) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add(ErrorDescriptions.CLIENT_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), errors));
        } catch (Exception e) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add(ErrorDescriptions.ERROR_WHEN_SAVING_ACCOUNT);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), errors));
        }

    }

    @GetMapping(value = "/find", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> find(@Valid @RequestBody AccountFindRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = getErrorResponseResponseEntity(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        try {
            Optional<Account> account = null;

            if(request.getAccountId() != 0 )
                account = accountFindByUseCase.findById(request.getAccountId());

            if(!account.isPresent()){
                ArrayList<String> errors = new ArrayList<>();
                errors.add(ErrorDescriptions.ACCOUNT_NOT_FOUND);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorCodes.RECORD_NOT_FOUND, errors));
            }

            return ResponseEntity.status(HttpStatus.FOUND).body(accountPresentationMapper.findOneToResponse(account.get()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), null));
        }

    }

    @GetMapping(value = "/list", produces = "application/json")
    public ResponseEntity<?> list() {

        try {
            List<Account> accountList = null;
            List<AccountResponse> accountResponse = new ArrayList<>();

            accountList = accountListUseCase.getAccountList();

            if(accountList == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("No se pudo obtener la lista de cuentas", null));

            for (Account account :
                    accountList) {
                accountResponse.add(accountPresentationMapper.domainToResponse(account));
            }

            return ResponseEntity.status(HttpStatus.FOUND).body(accountResponse);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), null));
        }
    }

    @GetMapping(value = "/myaccounts", produces = "application/json")
    public ResponseEntity<?> myAccounts() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            List<MyAccountsResponse> myAccountsResponseList = myAccountsUseCase.getMyAccounts(username);

            return ResponseEntity.status(HttpStatus.FOUND).body(myAccountsResponseList);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), null));
        }
    }

    //TODO: list accounts

    //TODO: delete accounts

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
