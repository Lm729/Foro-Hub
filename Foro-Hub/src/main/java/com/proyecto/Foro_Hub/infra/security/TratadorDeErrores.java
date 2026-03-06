package com.proyecto.Foro_Hub.infra.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity tratarErrorValidacion(ValidacionException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
