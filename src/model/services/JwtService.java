package model.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {
    @Value ("${jwt.secret}")
    private String secret;
    @Value ("${jwt.expiration}")
    private long expirationMs;



    public String generateToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(expirationMs, ChronoUnit.MILLIS))
                .withClaim("role", userDetails.getAuthorities().stream().toList())
                .sign(Algorithm.HMAC256(secret));
    }

}
