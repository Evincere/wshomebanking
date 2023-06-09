package com.bbva.wshomebanking.application.services;

import com.bbva.wshomebanking.application.repository.IClientRepository;
import com.bbva.wshomebanking.application.usecases.client.IClientCreateUseCase;
import com.bbva.wshomebanking.application.usecases.client.IClientFindByUseCase;
import com.bbva.wshomebanking.application.usecases.client.IClientSaveUseCase;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.presentation.mapper.ClientPresentationMapper;
import com.bbva.wshomebanking.presentation.request.client.ClientCreateRequest;
import com.bbva.wshomebanking.presentation.response.client.ClientCreateResponse;
import com.bbva.wshomebanking.utilities.AppConstants;
import com.bbva.wshomebanking.utilities.exceptions.ExistingPersonalIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService implements IClientCreateUseCase, IClientFindByUseCase {

    private final IClientRepository clientRepository;
    private final ClientPresentationMapper clientMapper;

    @Override
    public ClientCreateResponse create(ClientCreateRequest request) throws ExistingPersonalIdException {

        // server side validations
        if (clientRepository.existsByPersonalId(request.getPersonalId())) {
            throw new ExistingPersonalIdException("DNI ya registrado");
        }

        // Creo la entidad cliente con los datos que tengo
        Client client = clientMapper.requestToDomain(request);

        // Creo la cuenta por default que nos exige el negocio
        Account account = Account.builder()
                .currency(AppConstants.getDefaultCurrency())
                .balance(BigDecimal.ZERO)
                .build();

        // Creo la relacion cliente cuenta y la agrego a una lista
        List<ClientAccount> clientAccountList = new ArrayList<ClientAccount>();
        clientAccountList.add(ClientAccount.builder()
                .client(client)
                .account(account)
                .accountHolderType(AppConstants.getDefaultHolderType())
                .build());

        // Asigno la relacion a los objetos
        client.setAccounts(clientAccountList);
        account.setClients(clientAccountList);

        clientRepository.saveClient(client);


        // response
        return clientMapper.domainToResponse(client);
    }

    @Override
    public Optional<Client> findById(int id) {
        return clientRepository.findById(id);
    }
}
