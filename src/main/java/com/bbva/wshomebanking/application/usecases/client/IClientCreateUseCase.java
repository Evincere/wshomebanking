package com.bbva.wshomebanking.application.usecases.client;

import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.presentation.request.client.ClientCreateRequest;
import com.bbva.wshomebanking.presentation.response.client.ClientCreateResponse;
import com.bbva.wshomebanking.utilities.exceptions.ExistingPersonalIdException;

public interface IClientCreateUseCase {

    ClientCreateResponse create(ClientCreateRequest request) throws ExistingPersonalIdException;

}
