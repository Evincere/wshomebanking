package com.bbva.wshomebanking.application.services;

import com.bbva.wshomebanking.application.repository.IClientRepository;
import com.bbva.wshomebanking.application.usecases.login.ILoginUseCase;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.presentation.request.login.LoginRequest;
import com.bbva.wshomebanking.security.CustomUserDetailsService;
import com.bbva.wshomebanking.utilities.ErrorCodes;
import com.bbva.wshomebanking.utilities.exceptions.InvalidLoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService implements ILoginUseCase {
    private final IClientRepository clientRepository;
    private final CustomUserDetailsService userDetailsService;
    @Override
    public Optional<UserDetails> login(LoginRequest request) throws InvalidLoginException {
        Optional<Client> client = clientRepository.findByPersonalId(request.getUsername());
        if(client.isPresent()){
            String hashedPassword = BCrypt.hashpw(request.getPassword(), client.get().getSalt());
            if(hashedPassword.equals(client.get().getPassword())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(client.get().getPersonalId());
                return  Optional.of(userDetails);
            }
            else
                throw new InvalidLoginException(ErrorCodes.INVALID_LOGIN);
        }
        else
            throw new InvalidLoginException(ErrorCodes.INVALID_LOGIN);

    }
}
