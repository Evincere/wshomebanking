package com.bbva.wshomebanking.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    private String nombre;
    private String apellido;
    private String email;
    private String direccion;
    private String telefono;
    private List<Cuenta> cuenta;

}
