package com.bbva.wshomebanking.application.usecases.client;

import com.bbva.wshomebanking.presentation.request.EnableDisableRequest;
import com.bbva.wshomebanking.presentation.response.account.AccountResponse;
import com.bbva.wshomebanking.presentation.response.client.ClientCreateResponse;
import com.bbva.wshomebanking.utilities.exceptions.ErrorWhenSavingException;
import com.bbva.wshomebanking.utilities.exceptions.RecordNotFoundException;

public interface IAccountEnableDisableUseCase {
    AccountResponse switchActive(EnableDisableRequest request) throws RecordNotFoundException, ErrorWhenSavingException;
}
