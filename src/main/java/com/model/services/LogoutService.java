package com.model.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {
    private final JwtService tokenService;
    private final TokenBlacklistService tokenBlacklistService;

    public LogoutService(JwtService tokenService, TokenBlacklistService tokenBlacklistService) {
        this.tokenService = tokenService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    public void logout (HttpServletRequest request) {
        String token = null;
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()){
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        }

        String jti = tokenService.extractJti(token);
        long expMs = tokenService.extractExp(token).getTime();
        long nowMs = System.currentTimeMillis();
        long ttl = (expMs - nowMs) / 1000;

        if (ttl > 0) {
            tokenBlacklistService.invalidar(jti, ttl);
            System.out.println("token invalidado");
        }

    }


}
