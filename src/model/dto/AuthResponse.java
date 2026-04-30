package model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


//DTO de de saida.
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type = "Bearer";

    public AuthResponse (String token){
        this.token = token;
    }
}
