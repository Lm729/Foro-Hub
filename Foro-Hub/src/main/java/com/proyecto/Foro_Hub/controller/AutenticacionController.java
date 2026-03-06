package com.proyecto.Foro_Hub.controller;

import com.proyecto.Foro_Hub.domain.usuario.Usuario;
import com.proyecto.Foro_Hub.dto.DatosLogin;
import com.proyecto.Foro_Hub.dto.DatosTokenJWT;
import com.proyecto.Foro_Hub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody DatosLogin datos) {

        var authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        datos.username(),
                        datos.password()
                );

        var authentication =
                authenticationManager.authenticate(authenticationToken);

        var usuario = (Usuario) authentication.getPrincipal();

        var tokenJWT = tokenService.generarToken(usuario);

        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }
}