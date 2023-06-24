package com.bbva.wshomebanking.application.usecases.login;

import com.bbva.wshomebanking.presentation.request.login.LoginRequest;
import com.bbva.wshomebanking.utilities.exceptions.InvalidLoginException;

public interface ILoginUseCase {
    public boolean login(LoginRequest request) throws InvalidLoginException;
}
