package com.bbva.wshomebanking.infrastructure.mapper;

import com.bbva.wshomebanking.domain.models.Cliente;
import com.bbva.wshomebanking.infrastructure.entities.ClienteCuentaEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClienteEntity;
import com.bbva.wshomebanking.infrastructure.entities.CuentaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component()
@RequiredArgsConstructor
public class ClienteEntityMapper {

    public ClienteEntity domainToEntity(Cliente cliente) {
        return ClienteEntity.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .direccion(cliente.getDireccion())
                .cuentas(new ArrayList<>())
                .build();
    }

    public Cliente entityToDomain(ClienteEntity clienteEntity) {
        Cliente cliente = new Cliente();
        cliente.setId(clienteEntity.getId());
        cliente.setNombre(clienteEntity.getNombre());
        cliente.setApellido(clienteEntity.getApellido());
        cliente.setEmail(clienteEntity.getEmail());
        cliente.setTelefono(clienteEntity.getTelefono());
        cliente.setDireccion(clienteEntity.getDireccion());
        cliente.setCuenta(new ArrayList<>());
        // TODO: esto lo copie tal cual estaba en el proyecto de Sergio, pero no anda
        /*
        for (ClienteCuentaEntity clienteCuentaEntity : clienteEntity.getCuentas()) {
            cliente.getCuenta().add();
        }
         */

        return cliente;
    }

}
