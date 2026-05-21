package com.controller;

import com.model.dto.AuthRequestDTO;
import com.model.dto.EmailDTO;
import com.model.dto.NewPasswordDTO;
import com.model.dto.RegisterDTO;
import com.model.services.LogoutService;
import com.model.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping ("/auth")
@RestController
public class AuthController {
    private final UserService userService;
    private final LogoutService logoutService;

    public AuthController (UserService userService, LogoutService logoutService) {
        this.userService = userService;
        this.logoutService = logoutService;
    }

    @PostMapping ("/login")
    public ResponseEntity  login (@RequestBody AuthRequestDTO requestDTO) {
        var auth = userService.authenticate(requestDTO);

        ResponseCookie cookie = ResponseCookie.from("token", auth.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .sameSite ("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Login realizado com sucesso!");
    }

    @PostMapping ("/register")
    public ResponseEntity register (@RequestBody RegisterDTO registerDTO) {
        userService.save(registerDTO);
        return ResponseEntity.status(201).build();

    }

    @PostMapping ("/logout")
    public ResponseEntity logout (HttpServletRequest request) {
        logoutService.logout(request);
        return ResponseEntity.status(200).build();

    }

    @GetMapping("/list")
    public List<EmailDTO> list () {
        return userService.listarTodos();
    }

    @PostMapping("/newPassword")
    public ResponseEntity newPassword (@RequestBody NewPasswordDTO newPasswordDTO) {
        userService.updatePassword(newPasswordDTO);
        return  ResponseEntity.status(200).build();
    }





}
