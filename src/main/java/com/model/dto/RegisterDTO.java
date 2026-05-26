package com.model.dto;

import com.model.entities.Role;

import java.time.LocalDate;

import static com.model.entities.Role.USER;

public record RegisterDTO (String email, String password, String full_name, LocalDate birth_data, Role role){
    public RegisterDTO {
        if (role == null) {
            role = USER;
        }
    }
}
