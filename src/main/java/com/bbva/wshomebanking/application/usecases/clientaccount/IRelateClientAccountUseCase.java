package com.bbva.wshomebanking.application.usecases.clientaccount;

import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.presentation.request.clientaccount.ClientAccountCreateRequest;

public interface IRelateClientAccountUseCase {
    ClientAccount relate(ClientAccountCreateRequest request);
}
