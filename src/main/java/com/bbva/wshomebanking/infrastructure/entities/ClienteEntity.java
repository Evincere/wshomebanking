package com.bbva.wshomebanking.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID id;
    private String nombre;
    private String apellido;
    private String email;
    private String direccion;
    private String telefono;
    @OneToMany(mappedBy = "cliente")
    private List<ClienteCuentaEntity> cuentas;


}
