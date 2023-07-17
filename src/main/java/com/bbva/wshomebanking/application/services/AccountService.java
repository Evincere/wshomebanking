package com.bbva.wshomebanking.application.services;

import com.bbva.wshomebanking.application.repository.IAccountRepository;
import com.bbva.wshomebanking.application.repository.IClientRepository;
import com.bbva.wshomebanking.application.usecases.account.IAccountCreateUseCase;
import com.bbva.wshomebanking.application.usecases.account.IAccountFindByUseCase;
import com.bbva.wshomebanking.application.usecases.account.IAccountListUseCase;
import com.bbva.wshomebanking.application.usecases.account.IMyAccountsUseCase;
import com.bbva.wshomebanking.application.usecases.client.IAccountEnableDisableUseCase;
import com.bbva.wshomebanking.application.usecases.client.IClientFindByUseCase;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.presentation.mapper.AccountPresentationMapper;
import com.bbva.wshomebanking.presentation.request.EnableDisableRequest;
import com.bbva.wshomebanking.presentation.response.account.AccountResponse;
import com.bbva.wshomebanking.presentation.response.account.MyAccountsResponse;
import com.bbva.wshomebanking.presentation.response.client.ClientCreateResponse;
import com.bbva.wshomebanking.utilities.ErrorCodes;
import com.bbva.wshomebanking.utilities.ErrorDescriptions;
import com.bbva.wshomebanking.utilities.exceptions.ClientNotFoundException;
import com.bbva.wshomebanking.utilities.exceptions.ErrorWhenSavingException;
import com.bbva.wshomebanking.utilities.exceptions.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.ErrorManager;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountCreateUseCase, IAccountFindByUseCase, IAccountListUseCase, IMyAccountsUseCase, IAccountEnableDisableUseCase {

    private final IAccountRepository accountRepository;
    private final IClientRepository clientRepository;
    private final IClientFindByUseCase clientFindByUseCase;
    private final AccountPresentationMapper accountMapper;

    @Override
    public Account create(int clientId,String currency) throws RecordNotFoundException {
        // get the client
        Optional<Client> client = clientFindByUseCase.findById(clientId);

        if(client.isPresent()) {
            Account account = new Account();
            account.setBalance(BigDecimal.ZERO);
            account.setCurrency(currency);
            account.setActive(true);
            return accountRepository.create(account, client.get());
        } else {
            throw new RecordNotFoundException(ErrorCodes.RECORD_NOT_FOUND);
        }


    }

    @Override
    public Optional<Account> findById(int id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> getAccountList() {
        return accountRepository.findAll();
    }

    @Override
    public List<MyAccountsResponse> getMyAccounts(String username) throws ClientNotFoundException {
        Client client = clientRepository.findByPersonalId(username).orElse(null);

        if(client == null){
            throw new ClientNotFoundException(ErrorDescriptions.CLIENT_NOT_FOUND);
        }

        List<ClientAccount> clientAccountList = client.getAccounts();
        List<MyAccountsResponse> myAccountsResponseList = new ArrayList<>();

        for (int i = 0; i < clientAccountList.size(); i++) {
            ClientAccount clientAccount = clientAccountList.get(i);
            MyAccountsResponse myAccountsResponse = MyAccountsResponse.builder()
                    .id(clientAccount.getAccount().getId())
                    .balance(clientAccount.getAccount().getBalance())
                    .currency(clientAccount.getAccount().getCurrency())
                    .holderType(clientAccount.getAccountHolderType())
                    .build();
            myAccountsResponseList.add(myAccountsResponse);
        }

        return myAccountsResponseList;
    }

    @Override
    public AccountResponse switchActive(EnableDisableRequest request) throws RecordNotFoundException, ErrorWhenSavingException {
        Optional<Account> currentAccount = accountRepository.findById(request.getId());
        if(currentAccount.isPresent()) {
            currentAccount.get().setActive(request.isActive());
        } else {
            throw new RecordNotFoundException(ErrorCodes.RECORD_NOT_FOUND);
        }

        Account savedAccount = accountRepository.updateAccount(currentAccount.get());
        for (ClientAccount clientAccount: currentAccount.get().getClients()) {
            savedAccount.getClients().add(clientAccount);
        }

        return accountMapper.domainToResponse(savedAccount);
    }
}
