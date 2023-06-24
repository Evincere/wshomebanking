package com.bbva.wshomebanking.application.usecases.client;

import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.presentation.request.client.ClientUpdateRequest;
import com.bbva.wshomebanking.presentation.response.client.ClientCreateResponse;
import com.bbva.wshomebanking.utilities.exceptions.ErrorWhenSavingException;
import com.bbva.wshomebanking.utilities.exceptions.ExistingPersonalIdException;
import com.bbva.wshomebanking.utilities.exceptions.RecordNotFoundException;

public interface IClientUpdateUseCase {

    public ClientCreateResponse update(ClientUpdateRequest client) throws ExistingPersonalIdException, ErrorWhenSavingException, RecordNotFoundException;

}
