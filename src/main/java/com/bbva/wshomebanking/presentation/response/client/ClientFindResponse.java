package com.bbva.wshomebanking.presentation.response.client;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ClientFindResponse {
    private int id;
    private String personalId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private List<Account> accountList;

}
