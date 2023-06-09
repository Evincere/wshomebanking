package com.bbva.wshomebanking.presentation.response.client;

import com.bbva.wshomebanking.domain.models.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    private Account account;
}
