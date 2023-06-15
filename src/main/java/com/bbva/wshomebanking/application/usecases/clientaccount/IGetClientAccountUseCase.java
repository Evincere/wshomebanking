package com.bbva.wshomebanking.application.usecases.clientaccount;

import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;

import java.util.Optional;

public interface IGetClientAccountUseCase {
    ClientAccount get(int clientId, int accountId);
}
