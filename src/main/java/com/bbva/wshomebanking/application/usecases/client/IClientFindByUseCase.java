package com.bbva.wshomebanking.application.usecases.client;

import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.presentation.request.client.ClientCreateRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IClientFindByUseCase {

    Optional<Client> findById(int id);

}
