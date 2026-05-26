package com.model.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {
    private final JwtService tokenService;
    private final TokenBlacklistService tokenBlacklistService;

    public LogoutService(JwtService tokenService, TokenBlacklistService tokenBlacklistService) {
        this.tokenService = tokenService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    public void logout (HttpServletRequest request, HttpServletResponse response) {
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

        Cookie clearCookie = new Cookie ("token", null);
        clearCookie.setHttpOnly(true);
        clearCookie.setPath("/");
        clearCookie.setMaxAge(0);
        response.addCookie (clearCookie);

    }


}
