package com.model.dto;

import lombok.Data;


//DTO de de saida.
@Data
public class AuthResponseDTO {
    private String token;

    public AuthResponseDTO(String token){
        this.token = token;
    }
}
