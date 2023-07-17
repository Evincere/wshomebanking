package com.bbva.wshomebanking.application.usecases.client;

import com.bbva.wshomebanking.presentation.request.EnableDisableRequest;
import com.bbva.wshomebanking.presentation.response.client.ClientCreateResponse;
import com.bbva.wshomebanking.utilities.exceptions.ErrorWhenSavingException;
import com.bbva.wshomebanking.utilities.exceptions.RecordNotFoundException;

public interface IClientEnableDisableUseCase {
    ClientCreateResponse switchActive(EnableDisableRequest request) throws RecordNotFoundException, ErrorWhenSavingException;
}
