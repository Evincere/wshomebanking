package com.bbva.wshomebanking.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {

    private int id;
    private String personalId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private boolean active;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String salt;
    @JsonIgnore
    private List<ClientAccount> accounts;

}
