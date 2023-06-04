package com.bbva.wshomebanking.application.usecases.client;

import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.presentation.request.client.ClientCreateRequest;

public interface IClientCreateUseCase {

    Client create(ClientCreateRequest request);

}
