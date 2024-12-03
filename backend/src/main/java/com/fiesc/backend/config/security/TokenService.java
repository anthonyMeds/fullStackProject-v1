package com.fiesc.backend.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fiesc.backend.config.exception.ServiceException;
import com.fiesc.backend.domain.entity.Pessoa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);


    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Pessoa pessoa) throws ServiceException {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(pessoa.getLogin())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException e) {
            logger.error(e.getMessage());
            throw new ServiceException("Erro ao validar usuário");
        }
    }

    public String validateToken(String token) throws ServiceException {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            return  JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e) {
            logger.error(e.getMessage());
            throw new ServiceException("Usuário inválido");
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
