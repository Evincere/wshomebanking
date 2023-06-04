package com.bbva.wshomebanking.domain.models;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {

    private UUID id;
    private String nombre;
    private String apellido;
    private String email;
    private String direccion;
    private String telefono;
    private List<Account> account;

}
