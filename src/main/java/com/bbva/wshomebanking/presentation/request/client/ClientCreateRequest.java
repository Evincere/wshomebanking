package com.bbva.wshomebanking.presentation.request.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ClientCreateRequest {

    @NotBlank(message = "el DNI no puede estar vacío")
    @Size (min = 7, max = 8, message = "El DNI debe tener 7 u 8 caracteres")
    private String personalId;
    @NotBlank(message = "el nombre no puede estar vacío")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "El nombre debe contener solo caracteres alfabéticos")
    @Size (min = 2, max = 30, message = "El nombre debe tener entre 2 y 30 caracteres")
    private String firstName;
    @NotBlank(message = "el apellido no puede estar vacío")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "El apellido debe contener solo caracteres alfabéticos")
    @Size(min = 2, max = 30, message = "El apellido debe tener entre 2 y 30 caracteres")
    private String lastName;
    @NotBlank(message = "el email no puede estar vacío")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "El email debe ser válido")
    private String email;
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono debe contener solo caracteres numéricos")
    private String phone;
    @Size(max = 50, message = "La dirección no puede tener más de 50 caracteres")
    private String address;

}
