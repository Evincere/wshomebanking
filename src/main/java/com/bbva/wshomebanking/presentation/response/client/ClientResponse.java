package com.bbva.wshomebanking.presentation.response.client;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class ClientResponse {

    private int id;
    private String personalId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
