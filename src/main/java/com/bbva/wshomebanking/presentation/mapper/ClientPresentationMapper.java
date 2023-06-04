package com.bbva.wshomebanking.presentation.mapper;

import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.presentation.request.client.ClientCreateRequest;
import com.bbva.wshomebanking.presentation.response.client.ClientResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class ClientPresentationMapper {

    public ClientResponse domainToResponse(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .nombre(client.getNombre())
                .apellido(client.getApellido())
                .email(client.getEmail())
                .telefono(client.getTelefono())
                .direccion(client.getDireccion())
                .build();
    }

    public Client requestToDomain(ClientCreateRequest request) {
        return Client.builder()
                .id(UUID.randomUUID())
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .account(new ArrayList<>())
                .build();
    }



}
