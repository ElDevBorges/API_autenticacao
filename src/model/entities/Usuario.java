package model.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table

public class Usuario implements UserDetails {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String login;

    @Column (nullable = false)
    private String password;

    public Usuario () {}
    public Usuario (String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin (String login) {
        this.login = login;
    }

    public String getPassword () {
        return password;
    }
    public void setPassword (String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }



    @Override
    public String getUsername() {
        return login;
    }



}
