package com.model.services;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenBlacklistService {
    private final StringRedisTemplate redis;


    public TokenBlacklistService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public void invalidar (String jti, long ttlSegundos) {
        redis.opsForValue().set (
                "blacklist: " + jti,
                "invalido",
                ttlSegundos,
                TimeUnit.SECONDS
        );
    }

    public Boolean estaInvalido (String jti) {
        return Boolean.TRUE.equals(
                redis.hasKey("blacklist:" + jti)
        );
    }
}
