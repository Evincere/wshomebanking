package com.bbva.wshomebanking.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String numero;
    private String departamento;
    private String piso;
    private String ciudad;
    private String codigoPostal;
    private String telefono;
    private String email;
    @OneToMany(mappedBy = "cliente")
    private List<ClienteCuenta> cuentas;


}
