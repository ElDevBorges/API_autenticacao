package com.model.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;


@Service
public class JwtService {
    @Value ("${jwt.secret}")
    private String secret;
    @Value ("${jwt.expiration}")
    private long expirationMs;



    public String generateToken(UserDetails userDetails) {
        return JWT.create()
                .withIssuer ("jwt.secret")
                .withSubject(userDetails.getUsername())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(expirationMs, ChronoUnit.MILLIS))
                .withClaim("ROLE", userDetails.getAuthorities().iterator().next().getAuthority())
                .withJWTId(UUID.randomUUID().toString())
                .sign(Algorithm.HMAC256(secret));
    }


    public String validToken (String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require (algorithm)
                    .withIssuer("jwt.secret")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private DecodedJWT decode (String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("jwt.secret")
                .build()
                .verify(token);
    }

    public boolean isTokenValid (String token, UserDetails userDetais) {
        try {
            String subject = extractUsername(token);
            return subject.equals(userDetais.getUsername()) && !isTokenExpired(token);
        } catch (JWTVerificationException e) {
            System.out.println("assinatura invalida ou token expirado");
            return false;
        }
    }

    public String extractUsername (String token) {
        return decode(token).getSubject();
    }

    public String extractJti (String token) {
        return decode(token).getId();
    }

    private boolean isTokenExpired(String token) {
        return decode(token).getExpiresAt()
                .before(new Date());
    }

    public Date extractExp (String token) {
        return decode(token).getExpiresAt();
    }




}
