package com.bbva.wshomebanking.application.usecases.transaction;

import com.bbva.wshomebanking.presentation.response.account.MyAccountsResponse;
import com.bbva.wshomebanking.utilities.exceptions.ClientNotFoundException;

import java.util.List;

public interface IMyTransactionsUseCase {
    List<MyAccountsResponse> getMyAccounts(String personalId) throws ClientNotFoundException;
}
