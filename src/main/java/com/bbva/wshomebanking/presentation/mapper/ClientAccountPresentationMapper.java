package com.bbva.wshomebanking.presentation.mapper;

import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.presentation.response.client.ClientResponse;
import com.bbva.wshomebanking.presentation.response.clientaccount.ClientAccountResponse;
import org.springframework.stereotype.Component;

@Component
public class ClientAccountPresentationMapper {

    public ClientAccountResponse domainToResponse(ClientAccount clientAccount) {
        return ClientAccountResponse.builder()
                .accountId(clientAccount.getAccount().getId())
                .personalId(clientAccount.getCliente().getPersonalId())
                .currency(clientAccount.getAccount().getCurrency())
                .holderType(clientAccount.getAccountHolderType())
                .build();
    }
}
