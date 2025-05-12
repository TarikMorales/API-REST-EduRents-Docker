package com.ingsoft.tf.api_edurents.dto.user;

import lombok.Data;

@Data
public class RegisterDTO {
    private String nombres;
    private String apellidos;
    private String correo;
    private String contrasena;
    private Integer id_carrera;
    private String codigo_universitario;
    private String foto_url;
    private Byte ciclo;
}
