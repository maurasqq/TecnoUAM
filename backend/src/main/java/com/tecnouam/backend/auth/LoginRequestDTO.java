package com.tecnouam.backend.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "USUARIO ES OBLIGATORIO")
        String username,

        @NotBlank(message = "CONTRASENIA ES OBLIGATORIA")
        String password
) {}