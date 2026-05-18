package com.model.repository;

import com.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository <User, Long> {
    Optional<User> findByEmail (String email);
    Boolean existsByEmail (String email);
    List findAll();
}
