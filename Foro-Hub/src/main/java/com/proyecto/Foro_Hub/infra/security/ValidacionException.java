package com.proyecto.Foro_Hub.infra.security;

public class ValidacionException extends RuntimeException {

    public ValidacionException(String mensaje) {
        super(mensaje);
    }

}