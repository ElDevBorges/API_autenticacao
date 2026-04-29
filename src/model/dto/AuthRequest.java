package model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank
    String full_name;

    @NotBlank
    String email;

    @NotBlank @Size(min = 6)
    String password;
}
