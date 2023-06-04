package com.bbva.wshomebanking.application.services;

import com.bbva.wshomebanking.application.repository.IClientRepository;
import com.bbva.wshomebanking.application.usecases.client.IClientCreateUseCase;
import com.bbva.wshomebanking.application.usecases.client.IClientSaveUseCase;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.presentation.mapper.ClientPresentationMapper;
import com.bbva.wshomebanking.presentation.request.client.ClientCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService implements IClientCreateUseCase, IClientSaveUseCase {

    private final IClientRepository clientRepository;
    private final ClientPresentationMapper clientMapper;

    @Override
    public Client create(ClientCreateRequest request) {
        String email = request.getEmail();
        if (clientRepository.existsByEmail(email)) {
            //TODO: implementar una excepcion descriptiva
            throw new RuntimeException("This client already exists");
        }

        return clientMapper.requestToDomain(request);
    }

    @Override
    public Client save(Client cliente) {
        return clientRepository.saveCliente(cliente);
    }

}
