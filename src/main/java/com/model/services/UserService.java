package com.model.services;


import com.model.dto.*;
import com.model.entities.Perfil;
import com.model.entities.User;
import com.model.repository.PerfilRepository;
import com.model.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private boolean userAuthenticated = false;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private AuthRequestDTO authRequestDTO;


    public UserService(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository, PasswordEncoder passwordEncoder, JwtService jwtService, CustomUserDetailsService customUserDetailsService, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
    }

    public List <EmailDTO> listarTodos() {
    List<User> lista = usuarioRepository.findAll();
    List<EmailDTO> listEmails = new ArrayList<>();

    for (User user : lista) {
        var userEmail = new EmailDTO(user.getEmail());
        listEmails.add(userEmail);
    }
        return  listEmails;


    }

    public Optional findById(long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario não existe");
        }
        return usuarioRepository.findById(id);
    }

    public PerfilDTO findByEmail (EmailDTO dto) {
       var user = usuarioRepository.findByEmail(dto.email())
               .orElseThrow (() -> new RuntimeException("Usuario não encontrado"));
       var perfil = user.getPerfil();

       return new PerfilDTO(
               perfil.getFull_name(),
               perfil.getBirth_data()
       );

    }

    public AuthResponseDTO authenticate (AuthRequestDTO requestDTO) {
        var authentication = authenticationManager.authenticate (new UsernamePasswordAuthenticationToken(requestDTO.email(), requestDTO.password()));
        return new AuthResponseDTO(jwtService.generateToken((User) authentication.getPrincipal()));
    }



    @Transactional
    public void save (RegisterDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Usuário com email ja cadastrado");
        }



        User user = new User();
        user.setEmail(dto.email());
        var passwordBCrypt = passwordEncoder.encode(dto.password());
        user.setPassword(passwordBCrypt);

        Perfil perfil = new Perfil();
        perfil.setFull_name(dto.full_name());
        perfil.setBirth_data(dto.birth_data());




        usuarioRepository.save(user);
        perfil.setUser(user);

        perfilRepository.save (perfil);
        user.setPerfil(perfil);

    }

    @Transactional
    public String delete (EmailDTO dto) {
        var user = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        var perfil = user.getPerfil();
        perfilRepository.delete(perfil);
       usuarioRepository.delete(user);


        return "Usuário " + user.getEmail() + " Deletado com sucesso!";

    }
}
