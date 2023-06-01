package com.bbva.wshomebanking.presentation.mapper;

import com.bbva.wshomebanking.domain.models.Cliente;
import com.bbva.wshomebanking.presentation.request.client.ClienteCreateRequest;
import com.bbva.wshomebanking.presentation.response.client.ClienteResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class ClientePresentationMapper {

    public ClienteResponse domainToResponse(Cliente client) {
        return ClienteResponse.builder()
                .id(client.getId())
                .nombre(client.getNombre())
                .apellido(client.getApellido())
                .email(client.getEmail())
                .telefono(client.getTelefono())
                .direccion(client.getDireccion())
                .build();
    }

    public Cliente requestToDomain(ClienteCreateRequest request) {
        return Cliente.builder()
                .id(UUID.randomUUID())
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .cuenta(new ArrayList<>())
                .build();
    }



}
