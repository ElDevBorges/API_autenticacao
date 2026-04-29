package model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface usuarioRepository extends JpaRepository {
    String findBylogin(String email);
    Boolean existsByLogin (String email);
}
