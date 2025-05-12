package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;

public interface AdminUserService {

    UserDTO registerUsuario(RegisterDTO datosRegistro);

}
