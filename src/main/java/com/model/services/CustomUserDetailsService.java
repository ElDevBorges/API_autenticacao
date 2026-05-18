package com.model.services;

import com.model.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository userRepository;

    public CustomUserDetailsService (UsuarioRepository usuarioRepository) {
        this.userRepository = usuarioRepository;
    }




    @Override
    public UserDetails loadUserByUsername (String email) {
        var userEmail = userRepository.findByEmail(email)
                .orElseThrow (() -> new UsernameNotFoundException("Usuário não encontrado"));

        return userEmail;
    }
}
