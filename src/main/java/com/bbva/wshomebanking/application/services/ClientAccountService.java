package com.bbva.wshomebanking.application.services;

import com.bbva.wshomebanking.application.repository.IClientAccountRepository;
import com.bbva.wshomebanking.application.usecases.clientaccount.IGetClientAccountUseCase;
import com.bbva.wshomebanking.application.usecases.clientaccount.IRelateClientAccountUseCase;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.presentation.request.clientaccount.ClientAccountCreateRequest;
import com.bbva.wshomebanking.utilities.exceptions.AccountNotFoundException;
import com.bbva.wshomebanking.utilities.exceptions.ClientNotFoundException;
import com.bbva.wshomebanking.utilities.exceptions.RelationshipNotCreatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientAccountService implements IRelateClientAccountUseCase, IGetClientAccountUseCase {
    private final IClientAccountRepository clientAccountRepository;
    @Override
    public ClientAccount relate(ClientAccountCreateRequest request) throws ClientNotFoundException, RelationshipNotCreatedException, AccountNotFoundException {
        ClientAccount savedClientAccount = clientAccountRepository.relateClientToAccount(
                request.getClientId(),
                request.getAccountId(),
                request.getHolderType()
        );
        return savedClientAccount;
    }

    @Override
    public ClientAccount get(int clientId, int accountId) {
        return clientAccountRepository.get(clientId, accountId);
    }
}
