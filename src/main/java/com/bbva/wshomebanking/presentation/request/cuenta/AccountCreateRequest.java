package com.bbva.wshomebanking.presentation.request.cuenta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AccountCreateRequest {

    private int clientId;

    @NotBlank(message = "la moneda no puede estar vacia")
    @Size(min = 3, max = 3, message = "El nombre debe tener entre 2 y 30 caracteres")
    private String currency;

}
