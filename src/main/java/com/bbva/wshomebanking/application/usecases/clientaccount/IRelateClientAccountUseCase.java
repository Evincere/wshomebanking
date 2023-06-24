package com.bbva.wshomebanking.application.usecases.clientaccount;

import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.presentation.request.clientaccount.ClientAccountCreateRequest;
import com.bbva.wshomebanking.utilities.exceptions.AccountNotFoundException;
import com.bbva.wshomebanking.utilities.exceptions.ClientNotFoundException;
import com.bbva.wshomebanking.utilities.exceptions.RelationshipNotCreatedException;

public interface IRelateClientAccountUseCase {
    ClientAccount relate(ClientAccountCreateRequest request) throws ClientNotFoundException, RelationshipNotCreatedException, AccountNotFoundException;
}
