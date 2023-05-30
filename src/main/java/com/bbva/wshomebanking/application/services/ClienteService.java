package com.bbva.wshomebanking.application.services;

import com.bbva.wshomebanking.application.usecases.cliente.IClienteCreateUseCase;
import com.bbva.wshomebanking.application.usecases.cliente.IClienteSaveUseCase;
import com.bbva.wshomebanking.domain.models.Cliente;
import com.bbva.wshomebanking.presentation.request.client.ClienteCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService implements IClienteCreateUseCase, IClienteSaveUseCase {

    @Override
    public Cliente create(ClienteCreateRequest request) {
        return null;
    }

    @Override
    public Cliente save(Cliente client) {
        return null;
    }
}
