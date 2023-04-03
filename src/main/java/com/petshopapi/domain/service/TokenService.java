package com.petshopapi.domain.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.petshopapi.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    public String gerarToken(Usuario usuario) {
        return JWT.create()
                .withIssuer("PetShop")
                .withSubject(usuario.getUsername())
                .withClaim("id", usuario.getIdUsuario())
                .withExpiresAt(LocalDateTime.now()
                                            .plusMinutes(10)
                                            .toInstant(ZoneOffset.of("-03:00"))
                ).sign(Algorithm.HMAC256("secretKey"));
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256("secretKey"))
                                    .withIssuer("PetShop")
                                    .build().verify(token).getSubject();
    }
}
