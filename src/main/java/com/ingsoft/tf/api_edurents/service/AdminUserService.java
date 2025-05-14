package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.user.RecoverPasswordDTO;
import com.ingsoft.tf.api_edurents.dto.user.LoginDTO;
import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;

public interface AdminUserService {

    UserDTO cambioContrasenaUsuario(Integer id, RecoverPasswordDTO nuevosDatos);

    UserDTO loginUsuario(LoginDTO datosLogin);

}
