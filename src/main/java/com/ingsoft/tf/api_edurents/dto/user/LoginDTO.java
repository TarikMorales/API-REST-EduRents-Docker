package com.ingsoft.tf.api_edurents.dto.user;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class LoginDTO {
    @NonNull
    @Size(min = 5, max = 30)
    private String correo;

    @NonNull
    @Size(min = 5, max = 30)
    private String contrasena;
}
