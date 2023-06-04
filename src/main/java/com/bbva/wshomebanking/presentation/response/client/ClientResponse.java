package com.bbva.wshomebanking.presentation.response.client;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class ClientResponse {

    private UUID id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
}
