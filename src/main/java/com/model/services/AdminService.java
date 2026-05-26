package com.model.services;

import com.model.dto.EmailDTO;
import com.model.entities.User;
import com.model.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private final UsuarioRepository usuarioRepository;

    public AdminService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<EmailDTO> listarTodos() {
        List<User> lista = usuarioRepository.findAll();
        List<EmailDTO> listEmails = new ArrayList<>();

        for (User user : lista) {
            var userEmail = new EmailDTO(user.getEmail());
            listEmails.add(userEmail);
        }
        return  listEmails;


    }

}
