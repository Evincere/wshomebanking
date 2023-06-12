package com.bbva.wshomebanking.application.services;

import com.bbva.wshomebanking.application.repository.IClientAccountRepository;
import com.bbva.wshomebanking.application.usecases.clientaccount.IRelateClientAccountUseCase;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.presentation.request.clientaccount.ClientAccountCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientAccountService implements IRelateClientAccountUseCase {
    private final IClientAccountRepository clientAccountRepository;
    @Override
    public ClientAccount relate(ClientAccountCreateRequest request) {
        ClientAccount savedClientAccount = clientAccountRepository.relateClientToAccount(
                request.getClientId(),
                request.getAccountId(),
                request.getHolderType()
        );
        return savedClientAccount;
    }
}
