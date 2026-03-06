package com.proyecto.Foro_Hub.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.proyecto.Foro_Hub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generarToken(Usuario usuario) {

        System.out.println("Secret usada para generar: " + secret);

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withSubject(usuario.getUsername())
                    .withIssuedAt(new Date())
                    .withExpiresAt(
                            new Date(System.currentTimeMillis() + expiration)
                    )
                    .sign(algorithm);

        } catch (Exception e) {
            throw new RuntimeException("Error al generar token JWT", e);
        }
    }

    public String validarToken(String token) {

        System.out.println("Secret usada para validar: " + secret);

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (Exception e) {
            return null;
        }
    }
}