package com.bbva.wshomebanking.infrastructure.entities;

import com.bbva.wshomebanking.domain.models.embeddable.ClienteCuentaId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "transacciones")
public class TransaccionEntity {

    /*@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID id;*/
    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "clienteId", referencedColumnName = "clienteId"),
            @JoinColumn(name = "cuentaId", referencedColumnName = "cuentaId")
    })
    private ClienteCuentaEntity clienteCuentaEntity;

    private String tipo;
    private BigDecimal monto;


}
