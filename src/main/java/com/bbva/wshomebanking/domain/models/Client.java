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

    private int id;
    private String personalId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private List<Account> accounts;

}
