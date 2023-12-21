package com.teamoff.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.teamoff.api.model.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    public String generateToken(Auth auth){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Team Off")
                    .withClaim("auth_id", auth.getId().toString())
                    .withClaim("user_id", auth.getUser().getId().toString())
                    .withExpiresAt(expireDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while generating JWT Token", exception);
        }
    }

    public String getClaim(String tokenJWT, String claim){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("API Team Off")
                    .build()
                    .verify(tokenJWT)
                    .getClaim(claim)
                    .asString();
        } catch (JWTVerificationException exception) {
            System.out.println("token="+tokenJWT);
            throw new RuntimeException("Token invalid or expired");
        }
    }

    private Instant expireDate() {
        var now = LocalDateTime.now();
        return now.plusHours(12).toInstant(ZoneId.systemDefault().getRules().getOffset(now));
    }
}
