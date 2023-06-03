package com.bbva.wshomebanking.application.services;

import com.bbva.wshomebanking.application.repository.IClienteRepository;
import com.bbva.wshomebanking.application.usecases.cliente.IClienteCreateUseCase;
import com.bbva.wshomebanking.application.usecases.cliente.IClienteSaveUseCase;
import com.bbva.wshomebanking.domain.models.Cliente;
import com.bbva.wshomebanking.presentation.mapper.ClientePresentationMapper;
import com.bbva.wshomebanking.presentation.request.client.ClienteCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService implements IClienteCreateUseCase, IClienteSaveUseCase {

    private final IClienteRepository clientRepository;
    private final ClientePresentationMapper clientMapper;

    @Override
    public Cliente create(ClienteCreateRequest request) {
        String email = request.getEmail();
        if (clientRepository.existsByEmail(email)) {
            //TODO: implementar una excepcion descriptiva
            throw new RuntimeException("This client already exists");
        }

        return clientMapper.requestToDomain(request);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clientRepository.saveCliente(cliente);
    }

}
