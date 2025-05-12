package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.user.LoginDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;

public interface AdminUserService {

    UserDTO loginUsuario(LoginDTO datosLogin);

}
