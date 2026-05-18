package com.model.dto;

import java.time.LocalDate;

public record RegisterDTO (String email, String password, String full_name, LocalDate birth_data){
}
