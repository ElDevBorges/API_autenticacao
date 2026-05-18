package com.model.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table (name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column (nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private Role role = Role.USER;

    @OneToOne (mappedBy = "user")
    private Perfil perfil;


    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Perfil getPerfil () {
        return perfil;
    }

    public void setPerfil (Perfil perfil) {
        this.perfil = perfil;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }

    public Role getRole () {
        return role;
    }

    public void setRole (Role role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword () {
        return password;
    }
    public void setPassword (String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var nameRole = getRole().name();
        return List.of(new SimpleGrantedAuthority(nameRole));
    }



    @Override
    public String getUsername() {
        return email;
    }



}
