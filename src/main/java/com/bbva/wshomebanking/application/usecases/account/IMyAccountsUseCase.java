package com.bbva.wshomebanking.application.usecases.account;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.presentation.response.account.MyAccountsResponse;
import com.bbva.wshomebanking.utilities.exceptions.ClientNotFoundException;

import java.util.List;

public interface IMyAccountsUseCase {
    List<MyAccountsResponse> getMyAccounts(String personalId) throws ClientNotFoundException;
}
