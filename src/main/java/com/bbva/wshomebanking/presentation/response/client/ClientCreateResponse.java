package com.bbva.wshomebanking.presentation.response.client;

import com.bbva.wshomebanking.domain.models.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ClientCreateResponse {

    private int id;
    private String personalId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private boolean active;
    private List<Account> accounts;
}
