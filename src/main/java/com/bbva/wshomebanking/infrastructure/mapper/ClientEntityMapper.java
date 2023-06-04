package com.bbva.wshomebanking.infrastructure.mapper;

import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component()
@RequiredArgsConstructor
public class ClientEntityMapper {

    public ClientEntity domainToEntity(Client cliente) {
        return ClientEntity.builder()
                .id(cliente.getId())
                .personalId(cliente.getPersonalId())
                .firstName(cliente.getFirstName())
                .lastName(cliente.getLastName())
                .email(cliente.getEmail())
                .phone(cliente.getPhone())
                .address(cliente.getAddress())
                .accounts(new ArrayList<>())
                .build();
    }

    public Client entityToDomain(ClientEntity clientEntity) {
        Client cliente = new Client();
        cliente.setId(clientEntity.getId());
        cliente.setPersonalId(clientEntity.getPersonalId());
        cliente.setFirstName(clientEntity.getFirstName());
        cliente.setLastName(clientEntity.getLastName());
        cliente.setEmail(clientEntity.getEmail());
        cliente.setPhone(clientEntity.getPhone());
        cliente.setAddress(clientEntity.getAddress());
        cliente.setAccounts(new ArrayList<>());

        return cliente;
    }

}
