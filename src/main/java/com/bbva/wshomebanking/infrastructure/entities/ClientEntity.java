package com.bbva.wshomebanking.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Clients")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String personalId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private String password;
    private String salt;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<ClientAccountEntity> clientAccounts;


}
