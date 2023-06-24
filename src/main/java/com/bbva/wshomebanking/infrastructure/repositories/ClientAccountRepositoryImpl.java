package com.bbva.wshomebanking.infrastructure.repositories;

import com.bbva.wshomebanking.application.repository.IClientAccountRepository;
import com.bbva.wshomebanking.application.usecases.account.IAccountFindByUseCase;
import com.bbva.wshomebanking.application.usecases.client.IClientFindByUseCase;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.infrastructure.entities.AccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountId;
import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import com.bbva.wshomebanking.infrastructure.mapper.AccountEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.ClientAccountEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.ClientEntityMapper;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IClientAccountSpringRepository;
import com.bbva.wshomebanking.utilities.ErrorCodes;
import com.bbva.wshomebanking.utilities.exceptions.AccountNotFoundException;
import com.bbva.wshomebanking.utilities.exceptions.ClientNotFoundException;
import com.bbva.wshomebanking.utilities.exceptions.RecordNotFoundException;
import com.bbva.wshomebanking.utilities.exceptions.RelationshipNotCreatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientAccountRepositoryImpl implements IClientAccountRepository {

    public final IClientAccountSpringRepository clientAccountSpringRepository;
    private final IClientFindByUseCase clientFindByUseCase;
    private final IAccountFindByUseCase accountFindByUseCase;
    public final ClientEntityMapper clientEntityMapper;
    public final AccountEntityMapper accountEntityMapper;
    public final ClientAccountEntityMapper clientAccountEntityMapper;
    @Override
    public ClientAccount relateClientToAccount(int clientId, int accountId, String holderType) throws ClientNotFoundException, AccountNotFoundException, RelationshipNotCreatedException {
        // get the client and account received by params
        Optional<Client> client = clientFindByUseCase.findById(clientId);
        if(!client.isPresent()){
            throw new ClientNotFoundException(ErrorCodes.RECORD_NOT_FOUND);
        }
        Optional<Account> account = accountFindByUseCase.findById(accountId);
        if(!account.isPresent()){
            throw new AccountNotFoundException(ErrorCodes.RECORD_NOT_FOUND);
        }

        try {
            // convert the domain object into entities
            ClientEntity clientEntity = clientEntityMapper.domainToEntity(client.get());
            AccountEntity accountEntity = accountEntityMapper.domainToEntity(account.get());
            // create the client/account id
            ClientAccountId clientAccountId = new ClientAccountId();
            clientAccountId.setAccountId(accountEntity.getId());
            clientAccountId.setClientId(clientEntity.getId());
            // create the relationship to persist
            ClientAccountEntity clientAccountEntity = new ClientAccountEntity();
            clientAccountEntity.setId(clientAccountId);
            clientAccountEntity.setAccount(accountEntity);
            clientAccountEntity.setClient(clientEntity);
            clientAccountEntity.setHolderType(holderType);
            // persist the relationship
            ClientAccountEntity savedClientAccount = clientAccountSpringRepository.save(clientAccountEntity);

            return clientAccountEntityMapper.entityToDomain(savedClientAccount);

        } catch(Exception e) {
            throw new RelationshipNotCreatedException(ErrorCodes.COULD_NOT_RELATE_CLIENT_ACCOUNT);
        }

    }

    @Override
    public ClientAccount get(int clientId, int account) {
        ClientAccountId clientAccountId = new ClientAccountId(account, clientId);
        Optional<ClientAccountEntity> clientAccountEntity = clientAccountSpringRepository.findById(clientAccountId);
        clientAccountSpringRepository.save(clientAccountEntity.get());
        return clientAccountEntityMapper.entityToDomain(clientAccountEntity.get());
    }
}
