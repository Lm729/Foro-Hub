package com.proyecto.Foro_Hub.dto;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(

        String titulo,
        String mensaje

) {
}