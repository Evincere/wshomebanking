package com.bbva.wshomebanking.application.usecases.login;

import com.bbva.wshomebanking.presentation.request.login.LoginRequest;
import com.bbva.wshomebanking.utilities.exceptions.InvalidLoginException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface ILoginUseCase {
    public Optional<UserDetails> login(LoginRequest request) throws InvalidLoginException;
}
