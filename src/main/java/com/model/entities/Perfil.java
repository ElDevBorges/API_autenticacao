package com.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String full_name;

    @Column (nullable = false)
    private LocalDate birth_data;

    @OneToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    public Perfil () {
    }
}
