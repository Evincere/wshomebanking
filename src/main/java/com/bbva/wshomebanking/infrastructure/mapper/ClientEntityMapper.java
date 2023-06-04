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
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .direccion(cliente.getDireccion())
                .cuentas(new ArrayList<>())
                .build();
    }

    public Client entityToDomain(ClientEntity clientEntity) {
        Client cliente = new Client();
        cliente.setId(clientEntity.getId());
        cliente.setNombre(clientEntity.getNombre());
        cliente.setApellido(clientEntity.getApellido());
        cliente.setEmail(clientEntity.getEmail());
        cliente.setTelefono(clientEntity.getTelefono());
        cliente.setDireccion(clientEntity.getDireccion());
        cliente.setAccount(new ArrayList<>());
        // TODO: esto lo copie tal cual estaba en el proyecto de Sergio, pero no anda
        /*
        for (ClienteCuentaEntity clienteCuentaEntity : clienteEntity.getCuentas()) {
            cliente.getCuenta().add();
        }
         */

        return cliente;
    }

}
