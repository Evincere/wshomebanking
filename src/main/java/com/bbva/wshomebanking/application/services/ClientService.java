package com.bbva.wshomebanking.application.services;

import com.bbva.wshomebanking.application.repository.IClientRepository;
import com.bbva.wshomebanking.application.usecases.client.IClientCreateUseCase;
import com.bbva.wshomebanking.application.usecases.client.IClientFindByUseCase;
import com.bbva.wshomebanking.application.usecases.client.IClientSaveUseCase;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.presentation.mapper.ClientPresentationMapper;
import com.bbva.wshomebanking.presentation.request.client.ClientCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService implements IClientCreateUseCase, IClientSaveUseCase, IClientFindByUseCase {

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
    public Client save(Client cliente, Account account) {
        return clientRepository.saveCliente(cliente, account);
    }

    @Override
    public Optional<Client> findById(int id) {
        return clientRepository.findById(id);
    }
}
