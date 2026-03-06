package com.proyecto.Foro_Hub.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionUsuario(

        @NotBlank
        String username,

        @NotBlank
        String password
) {
}
