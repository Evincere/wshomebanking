package com.bbva.wshomebanking.application.usecases.cliente;

import com.bbva.wshomebanking.domain.models.Cliente;
import com.bbva.wshomebanking.presentation.request.client.ClienteCreateRequest;

public interface IClienteCreateUseCase {

    Cliente create(ClienteCreateRequest request);

}
