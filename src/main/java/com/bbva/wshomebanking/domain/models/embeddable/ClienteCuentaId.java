package com.bbva.wshomebanking.domain.models.embeddable;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ClienteCuentaId implements Serializable {

    private UUID clienteId;
    private UUID cuentaId;

}
