package com.bbva.wshomebanking.infrastructure.repositories;

import com.bbva.wshomebanking.application.repository.IClientAccountRepository;
import com.bbva.wshomebanking.application.repository.IClientRepository;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountId;
import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import com.bbva.wshomebanking.infrastructure.entities.AccountEntity;
import com.bbva.wshomebanking.infrastructure.mapper.AccountEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.ClientAccountEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.ClientEntityMapper;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IAccountSpringRepository;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IClientAccountSpringRepository;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IClientSpringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements IClientRepository {

    private final IClientSpringRepository clienteSpringRepository;
    private final IAccountSpringRepository accountSpringRepository;
    private final IClientAccountSpringRepository clientAccountSpringRepository;
    private final ClientEntityMapper clientEntityMapper;
    private final AccountEntityMapper accountEntityMapper;
    private final ClientAccountEntityMapper clientAccountEntityMapper;


    @Override
    public Client saveClient(Client client) {

        ClientEntity clientEntity = clientEntityMapper.domainToEntity(client);
        ClientEntity savedClient = clienteSpringRepository.save(clientEntity);

        AccountEntity accountEntity = accountEntityMapper.domainToEntity(client.getAccounts().get(0).getAccount());
        AccountEntity savedAccount = accountSpringRepository.save(accountEntity);

        ClientAccountEntity clientAccountEntity = ClientAccountEntity.builder()
                .id(new ClientAccountId(savedAccount.getId(), savedClient.getId()))
                .client(savedClient)
                .account(savedAccount)
                .holderType(client.getAccounts().get(0).getAccountHolderType())
                .build();

        clientAccountSpringRepository.save(clientAccountEntity);

        return null;
    }
    /*public Client saveCliente(Client cliente, Account account) {
        ClientEntity clientEntity = clientEntityMapper.domainToEntity(cliente);
        AccountEntity accountEntity = accountEntityMapper.domainToEntity(account);

        ClientAccountEntity clientAccountEntity = new ClientAccountEntity();
        clientAccountEntity.setClient(clientEntity);
        clientAccountEntity.setAccount(accountEntity);
        clientAccountEntity.setHolderType("TITULAR");
        ClientAccountId clientAccountId = new ClientAccountId();
        clientAccountId.setClientId(cliente.getId());
        clientAccountId.setAccountId(account.getId());
        clientAccountEntity.setId(clientAccountId);

        clienteSpringRepository.save(clientEntity);
        accountSpringRepository.save(accountEntity);
        clientAccountSpringRepository.save(clientAccountEntity);


        return clientEntityMapper.entityToDomain(clientEntity);
    }*/

    @Override
    public boolean existsByEmail(String email) {
        return clienteSpringRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPersonalId(String personalId) { return clienteSpringRepository.existsByPersonalId(personalId); }

    @Override
    public List<Client> getAll() {
        return null;
    }

    @Override
    public Optional<Client> findById(int id) {
        Optional<ClientEntity> optionalClient = clienteSpringRepository.findById(Integer.valueOf(id));
        if (optionalClient.isEmpty()) {
            return Optional.empty();
        }

        return optionalClient.map(clientEntityMapper::entityToDomain);
    }



    @Override
    public Client update(Client client) {
        return null;
    }
}
