package com.bbva.wshomebanking.application.usecases.cliente;

import com.bbva.wshomebanking.domain.models.Cliente;

public interface IClienteSaveUseCase {

    Cliente save(Cliente client);

}
