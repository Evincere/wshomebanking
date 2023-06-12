package com.bbva.wshomebanking.application.usecases.client;

import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.presentation.request.client.ClientFindRequest;
import com.bbva.wshomebanking.presentation.response.client.ClientFindResponse;

import java.util.List;

public interface IClientListUseCase {
    List<Client> getClientsList(ClientFindRequest filters);
}
