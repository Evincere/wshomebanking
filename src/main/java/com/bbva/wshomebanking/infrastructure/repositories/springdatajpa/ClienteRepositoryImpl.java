package com.bbva.wshomebanking.infrastructure.repositories.springdatajpa;

import com.bbva.wshomebanking.application.repository.IClienteRepository;
import com.bbva.wshomebanking.domain.models.Cliente;
import com.bbva.wshomebanking.domain.models.Cuenta;
import com.bbva.wshomebanking.infrastructure.entities.ClienteCuentaEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClienteEntity;
import com.bbva.wshomebanking.infrastructure.entities.CuentaEntity;
import com.bbva.wshomebanking.infrastructure.mapper.ClienteEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ClienteRepositoryImpl implements IClienteRepository {

    private final IClientSpringRepository clienteSpringRepository;
    private final ClienteEntityMapper clienteEntityMapper;


    @Override
    public Cliente saveCliente(Cliente cliente) {
        ClienteEntity clientEntity = clienteEntityMapper.domainToEntity(cliente);
        List<CuentaEntity> accountEntities = new ArrayList<CuentaEntity>();
        clientEntity.setCuentas(new ArrayList<ClienteCuentaEntity>());

        clienteSpringRepository.save(clientEntity);

        return clienteEntityMapper.entityToDomain(clientEntity);
    }

    @Override
    public List<Cliente> getAll() {
        return null;
    }

    @Override
    public Cliente findById(UUID id) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    /*@Override
    public boolean existsByEmailAndLastNameAndFirstName(String email, String apellido, String nombre) {
        return false;
    }*/

    @Override
    public Cliente update(Cliente client) {
        return null;
    }
}
